package com.apiecommerce.apiecomerce.server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(method = RequestMethod.GET)
public class PagesController {

    @GetMapping()
    public String homePage(){
        return "home.html";
    }

}
