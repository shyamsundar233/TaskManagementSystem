//$Id$
package com.taskswift.main.entity;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @Column(name = "username", nullable = false)
    private String username;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

	private Long userid;

	@ManyToOne
	@JoinColumn(name = "tenant_id")
	private Tenant tenant;

    // Getters and setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = bcryptPassword(password);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	@Override
	public String toString() {
		return "User{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", enabled=" + enabled +
				", userid=" + userid +
				", tenant=" + tenant +
				'}';
	}

	public static String bcryptPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		password = encoder.encode(password);
		return password;
	}
}