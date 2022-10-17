package com.example.libraryadminapp.core.domain.user.service.impl;

import com.example.libraryadminapp.core.domain.auth.utils.JwtUtils;
import com.example.libraryadminapp.core.domain.student.entity.Student;
import com.example.libraryadminapp.core.domain.student.repository.StudentRepository;
import com.example.libraryadminapp.core.domain.user.request.UserLoginRequestModel;
import com.example.libraryadminapp.core.domain.user.response.UserLoginResponseModel;
import com.example.libraryadminapp.core.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final StudentRepository studentRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @Override
    public UserLoginResponseModel login(final UserLoginRequestModel userLoginRequestModel) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequestModel.getUsername(), userLoginRequestModel.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwt = jwtUtils.generateJwtToken(authentication);

        return UserLoginResponseModel
                .builder()
                .jwtToken(jwt)
                .build();
    }
}
