package com.example.libraryadminapp.core.domain.user.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponseModel {

    private final String jwtToken;
}
