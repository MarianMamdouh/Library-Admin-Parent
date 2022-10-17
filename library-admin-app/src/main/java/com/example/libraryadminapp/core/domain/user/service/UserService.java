package com.example.libraryadminapp.core.domain.user.service;

import com.example.libraryadminapp.core.domain.user.request.UserLoginRequestModel;
import com.example.libraryadminapp.core.domain.user.response.UserLoginResponseModel;

public interface UserService {

    UserLoginResponseModel login(UserLoginRequestModel userLoginRequestModel);
}
