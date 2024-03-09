package com.taskswift.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ts")
public class ReactController {

    @GetMapping(value = {
    		"",
    		"/create",
            "/list",
            "/addUser"
    })
    public String goTest() {
        return "forward:/index.html";
    }
}
