package com.service.email.service.impl;

import com.service.email.dto.EmailDTO;
import com.service.email.dto.ResponseDTO;
import com.service.email.service.EmailService;
import com.service.email.util.UtilEmail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service("mailSenderCustom")
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final UtilEmail utilEmail;
    private final String emailFrom;
    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender,@Value("${spring.mail.username}") String emailFrom ,UtilEmail utilEmail)
    {
        this.javaMailSender = javaMailSender;
        this.emailFrom = emailFrom;
        this.utilEmail = utilEmail;
    }
    @Override
    public ResponseDTO sendEmail(EmailDTO emailDTO) throws MailException {

        log.info("[Ini][Generando Email Simple]");
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(this.emailFrom);
        email.setSubject(emailDTO.getAsunto());
        email.setText(emailDTO.getMensaje());
            for(String emailTo : emailDTO.getReceptores())
            {
                email.setTo(emailTo);
                log.info("[Pro][Enviando Email a {} ]", emailTo);
                javaMailSender.send(email);
            }
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Exitoso");
        responseDTO.setStatus(0);
        responseDTO.setPayload("Exitoso");
        return responseDTO;

    }

    @Override
    public ResponseDTO sendEmailWithHtmlTemplateBusiness(EmailDTO emailDTO) {
        return null;
    }

    @Override
    public ResponseDTO sendEmailWithSimpleHtml(EmailDTO emailDTO) throws MessagingException {
        MimeMessage mensaje = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje,true, "UTF-8");
        String plantilla = utilEmail.getPlantillaFromHtml(emailDTO.getMensaje());
        log.info("[Ini][Generando Email con html en string]");

        helper.setSubject(emailDTO.getAsunto());



        for (MultipartFile archivoAdjunto : emailDTO.getAdjuntos()) {
            int i = 1;
            ByteArrayResource archivoResource = null;

            try {
                byte[] contenido = archivoAdjunto.getBytes();

                archivoResource = new ByteArrayResource(contenido);

                String nombreAdjunto = archivoAdjunto.getOriginalFilename();

                if(contenido.length==0)
                {
                    log.info("Adjunto "+ nombreAdjunto + " contiene 0 bytes, no se enviara archivos vacios" );
                }

                if (nombreAdjunto != null && !nombreAdjunto.isEmpty()) {
                    helper.addAttachment(nombreAdjunto, archivoResource);
                } else {
                    helper.addAttachment("Adjunto" + i, archivoResource);
                }

            } catch (Exception e) {
                log.info("Error procesando adjunto: {}", e.getMessage());
            }
        }



        for(String emailTo : emailDTO.getReceptores())
        {
            helper.setTo(emailTo);
            log.info("[Pro][Enviando Email a {} ]", emailTo);
            helper.setText(plantilla, true);
            helper.addInline("imagen", new ClassPathResource("static/imagenCorreo.png"));
            javaMailSender.send(mensaje);
        }

        return ResponseDTO.builder().message("ok").status(0).payload("{}").build();


    }

}
