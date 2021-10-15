package com.jb.news.interfaces;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jb.news.model.User;

@Repository
public interface IUser extends CrudRepository<User, Integer> {
	
	public Optional<User> findBySuser(String suser);

}
