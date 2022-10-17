package com.example.libraryadminapp.entrypoint.user.facade.impl;

import com.example.libraryadminapp.core.domain.user.request.UserLoginRequestModel;
import com.example.libraryadminapp.core.domain.user.response.UserLoginResponseModel;
import com.example.libraryadminapp.core.domain.user.service.UserService;
import com.example.libraryadminapp.entrypoint.user.controller.request.UserLoginRequestDTO;
import com.example.libraryadminapp.entrypoint.user.controller.response.UserLoginResponseDTO;
import com.example.libraryadminapp.entrypoint.user.facade.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    @Override
    public UserLoginResponseDTO login(final UserLoginRequestDTO userLoginRequestDTO) {

        final UserLoginRequestModel userLoginRequestModel = buildUserLoginRequestModel(userLoginRequestDTO);

        final UserLoginResponseModel userLoginResponseModel = userService.login(userLoginRequestModel);

        return UserLoginResponseDTO
                .builder()
                .jwtToken(userLoginResponseModel.getJwtToken())
                .build();
    }

    private UserLoginRequestModel buildUserLoginRequestModel(final UserLoginRequestDTO userLoginRequestDTO) {

        return UserLoginRequestModel
                .builder()
                .username(userLoginRequestDTO.getUsername())
                .password(userLoginRequestDTO.getPassword()).build();
    }
}
