package com.uichesoh.authservice.service;

import com.uichesoh.authservice.dto.AuthUserDto;
import com.uichesoh.authservice.dto.NewUserDto;
import com.uichesoh.authservice.dto.RequestDto;
import com.uichesoh.authservice.dto.TokenDto;
import com.uichesoh.authservice.entities.AuthUser;
import com.uichesoh.authservice.repository.AuthUserRepository;
import com.uichesoh.authservice.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;

    public AuthUser save(NewUserDto dto){
        Optional<AuthUser> user = authUserRepository.findByUsername(dto.getUserName());
        if(user.isPresent()){
            return null;
        }
        String password = passwordEncoder.encode((dto.getPassword()));
        AuthUser authUser = AuthUser.builder()
                .username(dto.getUserName())
                .password(password)
                .role(dto.getRole())
                .build();
        return authUserRepository.save(authUser);
    }

    public TokenDto login(AuthUserDto dto){
        Optional<AuthUser> user = authUserRepository.findByUsername(dto.getUserName());
        if(!user.isPresent()){
            return null;
        }
        if(passwordEncoder.matches(dto.getPassword(),user.get().getPassword())){
            return new TokenDto(jwtProvider.createToken(user.get()));
        }
        return null;
    }

    public TokenDto validate(String token, RequestDto requestDto){
        if(!jwtProvider.validate(token,requestDto)){
            return null;
        }

        String username = jwtProvider.getUsernameFromToken(token);
        if(!authUserRepository.findByUsername(username).isPresent()){
            return null;
        }

        return new TokenDto(token);
    }
}
