package com.taskswift.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReactController {

    @GetMapping(value = {
    		"/",
    		"/create",
            "/list"
    })
    public String goTest() {
        return "forward:/index.html";
    }
}
