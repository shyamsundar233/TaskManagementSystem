//$Id$
package com.taskswift.main.controller;

import com.taskswift.main.model.UserRegistration;
import com.taskswift.main.security.EmailService;
import com.taskswift.main.util.UserUtil;
import jakarta.mail.MessagingException;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@CrossOrigin("http://localhost:3000/")
public class AuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private EmailService emailService;

	@GetMapping("/")
	public String redirectIndex(){
		return "redirect:/ts";
	}

	@GetMapping("/login")
	public String getLogin(Authentication auth) {
		if(auth == null) {
			logger.info(">>> Redirecting to login page");
			return "login-page";
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
			theModel.addAttribute("ExistingUsers", UserUtil.getExistingUsers());
			return "register-page";
		}else {
			logger.info(">>> Redirecting to home page from /register as the user is already logged in");
			return "redirect:/";
		}		
	}
	
	@PostMapping("/register")
	public String postRegisterUser(@ModelAttribute("User") UserRegistration userRegistration) {
		ResponseEntity<JSONObject> response = UserUtil.saveUser(userRegistration);
		logger.info(response.getBody().toJSONString());
		if(response.getStatusCode() == HttpStatus.OK){
			emailService.sendMail("shyamsundar.vj233@gmail.com", "Sample sub", "Sample body");
			logger.info(">>> Redirecting to /login as the user is registered");
			return "redirect:/login";
		}else{
			logger.info(">>> Redirecting to /login as the user is registered");
			return "redirect:/register?error";
		}
	}
	
}
