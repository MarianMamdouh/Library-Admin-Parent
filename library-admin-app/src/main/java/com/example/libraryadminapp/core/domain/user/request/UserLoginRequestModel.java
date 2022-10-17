package com.example.libraryadminapp.core.domain.user.request;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class UserLoginRequestModel {

    private final String username;

    private final String password;
}

