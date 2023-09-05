package com.matcha.nlulibrary.controller;

import com.matcha.nlulibrary.dto.AuthenticationRequest;
import com.matcha.nlulibrary.dto.AuthenticationResponse;
import com.matcha.nlulibrary.request.RegisterRequest;
import com.matcha.nlulibrary.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){

        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/login")

    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
