package com.uichesoh.authservice.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestDto {
    private String uri;
    private String method;
}
