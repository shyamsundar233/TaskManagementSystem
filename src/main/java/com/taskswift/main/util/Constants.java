//$Id$
package com.taskswift.main.util;

public class Constants {
	
	public static final String USER = "User";	
	public static final String TASK = "Task";
	public static final String USERNAMEPATTERN = "^[^\s]+[a-zA-Z0-9]+$";
	public static final String PASSWORDPATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()-_=+\\\\\\|\\[\\]{};:'\",.<>?]).{8,}$";
	public static final String EMAILPATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	public static final String ONLYALPHANUMERIC = "^[^\\s]+[a-zA-Z0-9\\s]*[a-zA-Z0-9]$";

	public static final String ANONYMOUS_USER = "anonymousUser";

	public static final String ANONYMOUS_ROLE = "ROLE_ANONYMOUS";

}
