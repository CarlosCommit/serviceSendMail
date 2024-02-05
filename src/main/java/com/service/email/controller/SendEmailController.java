package com.service.email.controller;

import com.service.email.dto.EmailDTO;
import com.service.email.dto.ResponseDTO;
import com.service.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1")
public class SendEmailController {

    private final EmailService emailService;
    @Autowired
    public SendEmailController(@Qualifier("mailSender")EmailService emailService)
    {
        this.emailService = emailService;
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<ResponseDTO> throwExceptionMail(MailException e)
    {
       ResponseDTO responseDTO = new ResponseDTO();
       responseDTO.setPayload("Error");
       responseDTO.setMessage("Error: " + e.getMessage());
       responseDTO.setStatus(-10);
       return ResponseEntity.ok()
               .contentType(MediaType.APPLICATION_JSON)
               .body(responseDTO);
    }

    @PostMapping("/envioEmail")
    public ResponseEntity<ResponseDTO> sendEmail(@RequestBody EmailDTO email)
    {
        ResponseDTO responseDTO = this.emailService.sendEmail(email);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDTO);
    }
}
