package com.service.email.service;

import com.service.email.dto.EmailDTO;
import com.service.email.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("mailSender")
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final String emailFrom;
    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender,@Value("${spring.mail.username}") String emailFrom )
    {
        this.javaMailSender = javaMailSender;
        this.emailFrom = emailFrom;
    }
    @Override
    public ResponseDTO sendEmail(EmailDTO emailDTO) throws MailException {

        log.info("[Ini][Generando Email]");
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

}
