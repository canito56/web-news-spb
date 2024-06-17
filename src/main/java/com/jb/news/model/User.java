package com.jb.news.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name="user")
public @Data class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_user;	
	
	@Column(name="suser")
	private String suser;
	
	@Column(name="password")
	private String password;
	
	@Column(name="first_name") 
	private String first_name;
	
	@Column(name="last_name") 
	private String last_name;
	
	@Column(name="email")
	private String email;
	
	@UpdateTimestamp
	@Column(name="date_created")
	private Timestamp date_created;

}
