package com.example.libraryadminapp.entrypoint.user.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponseDTO {

    private String jwtToken;
}
