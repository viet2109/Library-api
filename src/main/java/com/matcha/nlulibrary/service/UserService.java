package com.matcha.nlulibrary.service;

import com.matcha.nlulibrary.auth.AuthenticationRequest;
import com.matcha.nlulibrary.dao.UserRepository;
import com.matcha.nlulibrary.entity.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bcrypt;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.findByEmail(username).orElseThrow();
        return user;
    }
    public boolean isExist(String email){
        return userRepository.existsByEmail(email);
    }
    public boolean invalidUser(AuthenticationRequest request){
        // Kiểm tra xem user có tồn tại trong database không?
        UserDetails user = userRepository.findByEmail(request.getEmail()).orElse(null);
        // Kiểm tra mật khẩu nếu người dùng tồn tại
        if (user != null){
            if (bcrypt.matches(request.getPassword(), user.getPassword()))  return false;
        }
        return true;
    }


}
