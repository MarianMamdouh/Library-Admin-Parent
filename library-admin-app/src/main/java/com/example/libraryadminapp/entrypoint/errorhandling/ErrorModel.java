package com.example.libraryadminapp.entrypoint.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class ErrorModel {

    private String timestamp;
    private Integer status;
    private String error;
    private String exception;
    private String message;
    private String path;
}