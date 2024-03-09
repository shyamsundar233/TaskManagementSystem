package com.taskswift.main.controller;

import com.taskswift.main.model.UserRegistration;
import com.taskswift.main.util.UserUtil;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
@CrossOrigin("http://localhost:3000")
@SuppressWarnings("unchecked")
public class UserController {

    @GetMapping("/getAuthorities")
    public JSONObject getAuthorities(){
        JSONObject response = new JSONObject();
        response.put("Authorities", UserUtil.getAllRoles());
        return response;
    }

    @PostMapping("/saveUser")
    public ResponseEntity<JSONObject> postUser(@RequestBody UserRegistration user){
        return UserUtil.saveUser(user);
    }

}
