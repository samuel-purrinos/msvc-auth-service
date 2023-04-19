package com.uichesoh.authservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthUserDto {
    private String userName;
    private String password;
}
