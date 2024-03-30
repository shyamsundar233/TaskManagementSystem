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
import org.json.simple.JSONArray;
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

	public static boolean isUserLoggedIn;

	public static User currentUser;

	public static Authority currentAuthority;

	public UserUtil(UserService userService, AuthorityService authorityService, RolesService rolesService) {
		logger.info(">>> Initializing UserUtil fields");
		UserUtil.userService = userService;
		UserUtil.authorityService = authorityService;
		UserUtil.rolesService = rolesService;
		UserUtil.currentUser = new User();
		UserUtil.currentAuthority = new Authority();
		logger.info(">>> Initialized UserUtil fields");
	}

	public static void loadCurrentUserdata(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null || (Constants.ANONYMOUS_USER.equals(auth.getPrincipal()) && Constants.ANONYMOUS_ROLE.equals(auth.getAuthorities().toArray()[0].toString()))){
			UserUtil.isUserLoggedIn = false;
			UserUtil.currentUser = new User();
			UserUtil.currentAuthority = new Authority();
			TenantUtil.currentTenant = new Tenant();
		}else{
			UserUtil.isUserLoggedIn = true;
			org.springframework.security.core.userdetails.User currentUserDetails = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
			UserUtil.currentUser = userService.getUserByName(currentUserDetails.getUsername());
			UserUtil.currentAuthority = authorityService.getAuthorityForUser(currentUserDetails.getUsername());
			TenantUtil.currentTenant = UserUtil.currentUser.getTenant();
		}
	}

	public static ResponseEntity<JSONObject> saveUser(UserRegistration userRegistration) {
		JSONObject response = new JSONObject();
		HttpStatus status = HttpStatus.OK;
		try {
			validateInputs(userRegistration);

			Tenant tenant = getTenantForUser();

			User user = new User();
			user.setUsername(userRegistration.getUsername());
			user.setPassword(userRegistration.getPassword());
			user.setEmail(userRegistration.getEmail());
			user.setTenant(tenant);
			user.setEnabled(true);

			Authority authority = new Authority();
			authority.setUsername(userRegistration.getUsername());
			authority.setAuthority(userRegistration.getAuthority());

			if(isUserLoggedIn){
				user.setUserid(TenantUtil.getNextUniqueId());
				authority.setId(TenantUtil.getNextUniqueId());
			}else{
				user.setUserid(tenant.getCurrentUniqueId());
				authority.setId(tenant.getCurrentUniqueId() + 1);
				tenant.setCurrentUniqueId(tenant.getCurrentUniqueId() + 2);
				tenant.setActive(false);
				TenantUtil.saveTenant(tenant);
			}

			userService.saveUser(user);
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
		if(username.isEmpty() || username.length() > 20) {
			logger.error(">>> Invalid username length");
			throw new UserRegistrationExeception("Invalid username length");
		}else if(password.isEmpty() || password.length() > 20) {
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

	public static Tenant getTenantForUser(){
		if(currentUser.getUserid() == null){
			return TenantUtil.getNextActiveTenant();
		}else{
			return currentUser.getTenant();
		}
	}

	public static JSONObject getAllUsers(){
		JSONObject response = new JSONObject();
		List<User> users = userService.getAllUsers();
		JSONArray usersArr = new JSONArray();
		for(User user : users){
			JSONObject userObj = new JSONObject();
			userObj.put("userid", user.getUserid());
			userObj.put("username", user.getUsername());
			userObj.put("email", user.getEmail());
			userObj.put("authority", authorityService.getAuthorityForUser(user.getUsername()).getAuthority());
			usersArr.add(userObj);
		}
		response.put("Users", usersArr);
		return response;
	}

	public static User getUserById(Long userId){
		return userService.getUserById(userId);
	}
	
}
