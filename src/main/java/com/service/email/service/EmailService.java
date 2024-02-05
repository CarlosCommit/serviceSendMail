package com.service.email.service;

import com.service.email.dto.EmailDTO;
import com.service.email.dto.ResponseDTO;

public interface EmailService {

    public ResponseDTO sendEmail(EmailDTO emailDTO);
}
