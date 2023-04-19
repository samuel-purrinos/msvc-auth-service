package com.uichesoh.authservice.controller;

import com.uichesoh.authservice.dto.AuthUserDto;
import com.uichesoh.authservice.dto.NewUserDto;
import com.uichesoh.authservice.dto.RequestDto;
import com.uichesoh.authservice.dto.TokenDto;
import com.uichesoh.authservice.entities.AuthUser;
import com.uichesoh.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody AuthUserDto authUserDto){
        TokenDto tokenDto = authService.login(authUserDto);
        if(authUserDto == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestParam String token, @RequestBody RequestDto requestDto){
        TokenDto tokenDto = authService.validate(token,requestDto);
        if(tokenDto==null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tokenDto);
    }
    @PostMapping("/create")
    public ResponseEntity<AuthUser> create(@RequestBody NewUserDto newUserDto){
        AuthUser authUser = authService.save(newUserDto);
        if(authUser==null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(authUser);
    }
}
