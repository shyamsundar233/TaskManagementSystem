package com.taskswift.main.controller;

import com.taskswift.main.util.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ts")
public class ReactController {

    @GetMapping(value = {
    		"",
    		"/create",
            "/edit/**",
            "/list",
            "/listUser",
            "/view/**"
    })
    public String goTest() {
        return "forward:/index.html";
    }
}
