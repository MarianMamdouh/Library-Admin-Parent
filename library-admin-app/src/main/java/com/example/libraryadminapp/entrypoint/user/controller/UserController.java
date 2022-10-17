package com.example.libraryadminapp.entrypoint.user.controller;

import com.example.libraryadminapp.entrypoint.user.controller.request.UserLoginRequestDTO;
import com.example.libraryadminapp.entrypoint.user.facade.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = UserController.ROOT_PATH)
@AllArgsConstructor
@CrossOrigin
public class UserController {

    static final String ROOT_PATH = "/user";

    private final UserFacade userFacade;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody final UserLoginRequestDTO userLoginRequestDTO) throws Exception {

        return new ResponseEntity<>(userFacade.login(userLoginRequestDTO), HttpStatus.OK);
    }
}
