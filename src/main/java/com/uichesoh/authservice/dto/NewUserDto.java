package com.uichesoh.authservice.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewUserDto {
    private String userName;
    private String password;
    private String role;
}
