package com.auto.service.controllers;

import com.auto.service.entity.User;
import com.auto.service.payloads.*;
import com.auto.service.security.AuthService;
import com.auto.service.security.CurrentUser;
import com.auto.service.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    AuthenticationManager authenticate;

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public HttpEntity<?> register(@Valid @RequestBody ReqSignUp reqSignUp) {
        ApiResponse response = authService.register(reqSignUp);
        if (response.isSuccess()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(getApiToken(reqSignUp.getPhoneNumber(), reqSignUp.getPassword()));
        }
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response.getMessage());
    }

    @PostMapping("/verification")
    public ResponseEntity<?> verificationCode(@RequestBody CodeVerification verification, @CurrentUser User user) {
        ApiResponseModel response = authService.verifyCode(verification,user);
        ReqSignUp signUp = (ReqSignUp) response.getObject();
        if(response.isSuccess()) {
            return ResponseEntity.ok().body(response.getMessage());
        }
        else {
            return new ResponseEntity<>(response.getMessage(),HttpStatus.CONFLICT);
        }
    }


    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody ReqSignIn reqSignIn){
        return ResponseEntity.ok(getApiToken(reqSignIn.getPhoneNumber(),reqSignIn.getPassword()));
    }


    public HttpEntity<?> getApiToken(String phoneNumber, String password){
        Authentication authentication = authenticate.authenticate(
                new UsernamePasswordAuthenticationToken(phoneNumber, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
