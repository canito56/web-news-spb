package com.jb.news.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.news.interfaceService.IUserService;
import com.jb.news.interfaces.IUser;
import com.jb.news.model.User;

@Service
public class UserService implements IUserService {
	@Autowired
	private IUser data;
	private static String salt = "vamos river todavia CARAJO boquita puto!";

	@Override
	public Optional<User> getUser(String u) {
		return data.findBySuser(u);
	}

	@Override
	public int addUser(User u) {
		int res = 0;
		u.setPassword(getSecurePassword(u.getPassword()));
		User user = data.save(u);
		if (!user.equals(null)) {
			res = 1;
		}
		return res;
	}

	@Override
	public int updateUser(User u) {
		int res = 1; 
		if (data.save(u).equals(null)) {
			res = 0;
		}
		return res;
	}
	
	public String getSecurePassword(String passwordToHash) {		
		String generatedPassword = null;
	    try {MessageDigest md = MessageDigest.getInstance("SHA-512");
	         md.update(salt.getBytes(StandardCharsets.UTF_8));
	         byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
	         StringBuilder sb = new StringBuilder();
	         for(int i=0; i< bytes.length ;i++){
	        	 sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	         }
	         generatedPassword = sb.toString();
	    } catch (NoSuchAlgorithmException e){
	    	e.printStackTrace();
	    }		    
	    return generatedPassword;
	}
	
	public String parseBody(String body, String extractValue) {
		String value = null;
		String[] split1 = body.split("&");
		String[] split2 = split1[0].split("=");
		String[] split3 = split1[1].split("=");
		String[] split4 = split1[2].split("=");
		if (split2[0].equals(extractValue)) {
			value = split2[1];
		} else {
			if (split3[0].equals(extractValue)) {
				value = split3[1];
			} else {
				if (split4[0].equals(extractValue)) {
					value = split4[1];
				}
			}
		}
		return value;
	}

}
