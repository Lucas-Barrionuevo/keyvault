package com.keyVault.app.dto;
import java.util.Date;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UserResponse {
	private int id;
	private String email;
	private Date createdAt;
	
	public UserResponse() {
		super();
	}
	
	public UserResponse(int id,String email, Date createdAt) {
		super();
		this.id = id;
		this.email = email;
		this.createdAt = createdAt;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}
