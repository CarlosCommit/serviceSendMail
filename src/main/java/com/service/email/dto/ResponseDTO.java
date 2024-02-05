package com.service.email.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO {

    private String message;
    private Integer status;
    private Object payload;
}
