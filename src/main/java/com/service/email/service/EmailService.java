package com.service.email.service;

import com.service.email.dto.EmailDTO;
import com.service.email.dto.ResponseDTO;
import jakarta.mail.MessagingException;

public interface EmailService {

    public ResponseDTO sendEmail(EmailDTO emailDTO);
    public ResponseDTO sendEmailWithHtmlTemplateBusiness(EmailDTO emailDTO);

    public ResponseDTO sendEmailWithSimpleHtml(EmailDTO emailDTO) throws MessagingException;
}
