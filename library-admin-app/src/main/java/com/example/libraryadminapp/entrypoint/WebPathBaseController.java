package com.example.libraryadminapp.entrypoint;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Hidden
public class WebPathBaseController {

    @ResponseBody
    @GetMapping(value = "/dashboard")
    public RedirectView omsUi() {
        return new RedirectView("http://www.stationeryaplus.com");
    }

    @ResponseBody
    @GetMapping(value = "/login")
    public RedirectView loginUI() {
        return new RedirectView("http://www.stationeryaplus.com");
    }
}
