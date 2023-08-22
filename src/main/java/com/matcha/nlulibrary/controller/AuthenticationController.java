package com.matcha.nlulibrary.controller;

import com.matcha.nlulibrary.request.AuthenticationRequest;
import com.matcha.nlulibrary.auth.AuthenticationResponse;
import com.matcha.nlulibrary.request.RegisterRequest;
import com.matcha.nlulibrary.exception.UserAlreadyExistsException;
import com.matcha.nlulibrary.exception.UserNotExistException;
import com.matcha.nlulibrary.service.AuthenticationService;
import com.matcha.nlulibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        if (userService.isExist(request.getEmail())) throw new UserAlreadyExistsException("User " + request.getEmail()+ " đã tồn tại");
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ){
        if (userService.invalidUser(request)) throw new UserNotExistException("User không tồn tại");

        return ResponseEntity.ok(authenticationService.login(request));
    }
}
