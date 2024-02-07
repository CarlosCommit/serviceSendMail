package com.service.email.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
    @NotBlank(message = "El asunto es obligatorio")
    private String asunto;
    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;
    @NotNull(message = "Se debe enviar los receptores")
    private List<String> receptores;
    private List<MultipartFile> adjuntos;
}
