package com.matcha.nlulibrary.service;


import com.matcha.nlulibrary.auth.JwtTokenProvider;
import com.matcha.nlulibrary.dao.TokenRepository;

import com.matcha.nlulibrary.dao.UserRepository;
import com.matcha.nlulibrary.dto.AuthenticationRequest;
import com.matcha.nlulibrary.dto.AuthenticationResponse;
import com.matcha.nlulibrary.entity.Token;
import com.matcha.nlulibrary.entity.User;
import com.matcha.nlulibrary.exception.UserAlreadyExistsException;
import com.matcha.nlulibrary.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        if (userService.isExist(request.getEmail())) throw new UserAlreadyExistsException("User " + request.getEmail()+ " đã tồn tại");
        User user = User.builder()
                .className(request.getClassName())
                .dob(request.getDob())
                .school(request.getSchool())
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_USER")
                .build();
        repository.save(user);
        String jwtToken = tokenProvider.generateToken(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .user(user)
                .build();
    }



    public AuthenticationResponse login (@RequestBody AuthenticationRequest loginRequest){
        if (userService.invalidUser(loginRequest)) throw new UsernameNotFoundException("User không tồn tại");
        else{
            // Xác thực từ username và password.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            // Nếu không xảy ra exception tức là thông tin hợp lệ
            // Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // get User vuaf tao
            User currentUser =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            // Trả về jwt cho người dùng.
            String jwt = tokenProvider.generateToken((UserDetails) authentication.getPrincipal());
            revokeAllUserToken(currentUser);
            saveUserToken(currentUser, jwt);
            return new AuthenticationResponse( currentUser, jwt);
        }


    }
    public String logout() {
        // get User vuaf tao
//        User currentUser =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(currentUser == null);
//        if (currentUser == null){
//            throw new UsernameNotFoundException("The token has been blacklisted");
//        }else{
//            revokeAllUserToken(currentUser);
//            SecurityContextHolder.clearContext();
//            return "";
//        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User currentUser = (User) authentication.getPrincipal();
            // Xử lý đăng xuất ở đây
            revokeAllUserToken(currentUser);
            SecurityContextHolder.clearContext();
            return "";
        } else {
            throw new UsernameNotFoundException("The token has been blacklisted");
        }


    }
    private void revokeAllUserToken(User user){
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(token ->{
            token.setExpired(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
    // luu token cua nguoi dung
    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder().token(jwtToken).expired(false).user(user).build();
        tokenRepository.save(token);
    }



}
