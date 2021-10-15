package com.jb.news.interfaceService;

import java.util.Optional;

import com.jb.news.model.User;

public interface IUserService {

	public Optional<User> getUser(String u);
	public int addUser(User u);
	public int updateUser(User u);
	public String getSecurePassword(String p);
	public String parseBody(String b, String e); 	
	
}
