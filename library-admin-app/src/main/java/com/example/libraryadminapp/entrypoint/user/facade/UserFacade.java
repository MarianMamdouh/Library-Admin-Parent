package com.example.libraryadminapp.entrypoint.user.facade;

import com.example.libraryadminapp.entrypoint.user.controller.request.UserLoginRequestDTO;
import com.example.libraryadminapp.entrypoint.user.controller.response.UserLoginResponseDTO;

public interface UserFacade {

    UserLoginResponseDTO login (UserLoginRequestDTO userLoginRequestDTO);
}
