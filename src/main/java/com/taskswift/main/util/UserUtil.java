//$Id$
package com.taskswift.main.util;

import com.taskswift.main.entity.Authority;
import com.taskswift.main.entity.Tenant;
import com.taskswift.main.entity.User;
import com.taskswift.main.exception.UserRegistrationExeception;
import com.taskswift.main.model.UserRegistration;
import com.taskswift.main.service.AuthorityService;
import com.taskswift.main.service.RolesService;
import com.taskswift.main.service.UserService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@SuppressWarnings("unchecked")
public class UserUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(UserUtil.class);
	
	private static UserService userService;
	
	private static AuthorityService authorityService;
	
	private static RolesService rolesService;

	private static Long currentUserId;

	public UserUtil(UserService userService, AuthorityService authorityService, RolesService rolesService) {
		logger.info(">>> Initializing UserUtil fields");
		UserUtil.userService = userService;
		UserUtil.authorityService = authorityService;
		UserUtil.rolesService = rolesService;
		logger.info(">>> Initialized UserUtil fields");
	}

	public static Long getCurrentUserId(){
		if(currentUserId == null){
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if("anonymousUser".equals(auth.getPrincipal())){
				return null;
			}else{
				String userName = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
				return userService.getUserByName(userName).getUserid();
			}
		}else{
			return currentUserId;
		}
	}

	public static ResponseEntity<JSONObject> saveUser(UserRegistration userRegistration) {
		JSONObject response = new JSONObject();
		HttpStatus status = HttpStatus.OK;
		try {
			validateInputs(userRegistration);
			Tenant tenant = TenantUtil.getTenantForNewUser();
			User user = new User();
			user.setUsername(userRegistration.getUsername());
			user.setPassword(userRegistration.getPassword());
			user.setEmail(userRegistration.getEmail());
			user.setTenant(tenant);
			user.setEnabled(true);					
			userService.saveUser(user);

			if(tenant.isActive()){
				tenant.setActive(false);
				TenantUtil.saveTenant(tenant);
			}
			
			Authority authority = new Authority();
			authority.setUsername(userRegistration.getUsername());
			authority.setAuthority(userRegistration.getAuthority());
			authorityService.saveAuthority(authority);
			response.put(Constants.USER, "User Created Successfully");
		}catch(Exception e) {
			response.put(Constants.USER, e.getMessage());
			status = HttpStatus.NOT_ACCEPTABLE;
		}
		response.put("status", status.value());
		return ResponseEntity.status(status).body(response);
	}
	
	public static List<String> getAllRoles(){
		return rolesService.getAllRoles();
	}
	
	public static void validateInputs(UserRegistration user) {
		logger.info(">>> Validating inputs in User Registration payload");
		String username = user.getUsername();
		String password = user.getPassword();
		String email = user.getEmail();
		if(username.length() < 1 || username.length() > 20) {
			logger.error(">>> Invalid username length");
			throw new UserRegistrationExeception("Invalid username length");
		}else if(password.length() < 1 || password.length() > 20) {
			logger.error(">>> Invalid password length");
			throw new UserRegistrationExeception("Invalid password length");
		}else {
			String usernameRegex = Constants.USERNAMEPATTERN;
			Pattern pattern = Pattern.compile(usernameRegex);
			Matcher matcher = pattern.matcher(username);
			if(!matcher.matches()) {
				logger.error(">>> Invalid Username");
				throw new UserRegistrationExeception("Invalid Username");
			}
			matcher = pattern.matcher(password);
			if(!matcher.matches()) {
				logger.error(">>> Invalid Password");
				throw new UserRegistrationExeception("Invalid Password");
			}
			
			String passwordRegex = Constants.PASSWORDPATTERN;
			pattern = Pattern.compile(passwordRegex);
			matcher = pattern.matcher(password);
			if(!matcher.matches()) {
				logger.error(">>> Invalid Password");
				throw new UserRegistrationExeception("Invalid Password");
			}
			
			String emailRegex = Constants.EMAILPATTERN;
			pattern = Pattern.compile(emailRegex);
			matcher = pattern.matcher(email);
			if(!matcher.matches()) {
				logger.error(">>> Invalid Email");
				throw new UserRegistrationExeception("Invalid Email");
			}
		}
	}
	
}
