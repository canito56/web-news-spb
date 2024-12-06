package com.jb.news.interfaceservice;

import java.util.Optional;

import com.jb.news.model.User;

public interface IUserService {

	public Optional<User> getUser(String u);
	public void updateUser(User u);
	public String getSecurePassword(String p);
	public String parseBody(String b, String e); 	
	
}
