package com.example.libraryadminparent;

import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {
    @GetMapping("/")
    public String helloWorld() {
        return "Hello world I'm on the web!";
    }
}
