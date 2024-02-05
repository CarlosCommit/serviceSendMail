package com.service.email.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.service.email.dto.EmailDTO;
import com.service.email.dto.ResponseDTO;
import com.service.email.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/v1")
public class SendEmailController {

    private final EmailService emailService;
    private static Logger log = LoggerFactory.getLogger(SendEmailController.class);

    @Autowired
    public SendEmailController(@Qualifier("mailSenderCustom")EmailService emailService)
    {
        this.emailService = emailService;
    }

    //TODO: CONSIDERAR MOVER LOS HANDLERS A UN CONTROLLERADVICE
    @ExceptionHandler(MailException.class)
    public ResponseEntity<ResponseDTO> throwExceptionMail(MailException e)
    {
       ResponseDTO responseDTO = new ResponseDTO();
       responseDTO.setPayload("Error");
       responseDTO.setMessage("Error: " + e.getMessage());
       responseDTO.setStatus(-10);
       return ResponseEntity.internalServerError()
               .contentType(MediaType.APPLICATION_JSON)
               .body(responseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e)
    {
        List<String> errors = new ArrayList<>();
        e.getAllErrors().forEach( objectError -> errors.add(objectError.getDefaultMessage()));
        ResponseDTO responseDTO = new ResponseDTO();
        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode error = nodeFactory.objectNode();
        error.put("info",errors.toString());
        responseDTO.setPayload(error);
        responseDTO.setMessage("Error: " + e.getMessage());
        responseDTO.setStatus(-20);
        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDTO);
    }

    @PostMapping("/envioEmailSimple")
    public ResponseEntity<ResponseDTO> sendEmail(@RequestBody EmailDTO email)
    {
        ResponseDTO responseDTO = this.emailService.sendEmail(email);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDTO);
    }

    @PostMapping("/envioEmailBusiness")
    public ResponseEntity<ResponseDTO> sendEmailWithTemplate(@RequestBody EmailDTO email)
    {
        try
        {
            ResponseDTO responseDTO = this.emailService.sendEmailWithSimpleHtml(email);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }catch (Exception e)
        {
            log.info("Ocurrio un error : {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseDTO.builder().message("Error").status(-10).payload("{}").build());
        }

    }



}
