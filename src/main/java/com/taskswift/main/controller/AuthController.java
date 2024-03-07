//$Id$
package com.taskswift.main.controller;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.taskswift.main.model.UserRegistration;
import com.taskswift.main.util.UserUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin("http://localhost:3000/")
public class AuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@GetMapping("/login")
	public String getLogin(HttpServletRequest request, Authentication auth) {		
		if(auth == null) {
			logger.info(">>> Redirecting to login page");
			return "/authentication/login-page";
		}else {
			logger.info(">>> Redirecting to index page");
			return "redirect:/";
		}		
	}
	
	@GetMapping("/register")
	public String getRegister(Model theModel, Authentication auth) {
		if(auth == null) {
			logger.info(">>> Redirecting to User Registration Page");
			theModel.addAttribute("User", new UserRegistration());
			theModel.addAttribute("Authorities", UserUtil.getAllRoles());
			return "/authentication/register-page";
		}else {
			logger.info(">>> Redirecting to home page from /register as the user is already logged in");
			return "redirect:/";
		}		
	}
	
	@PostMapping("/register")
	public String postRegisterUser(@ModelAttribute("User") UserRegistration userRegistration) {
		JSONObject response = UserUtil.saveUser(userRegistration);
		logger.info(response.toJSONString());
		logger.info(">>> Redirecting to /login as the user is registered");
		return "redirect:/login";
	}
	
}
