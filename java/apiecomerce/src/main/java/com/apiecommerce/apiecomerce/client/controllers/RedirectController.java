package com.apiecommerce.apiecomerce.client.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectController {

    @RequestMapping("/")
    public String redirectToFrontend() {
        return "redirect:https://frontend-19y2.onrender.com"; // ou o domínio onde seu frontend está hospedado
    }

    @RequestMapping("/doc")
    public String redirectToDocs() {
        return "redirect:https://backend-g8j8.onrender.com/swagger-ui/index.html#/";
    }
}
