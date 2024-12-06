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
@Table(name="news")
public @Data class News {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_news")
	private int idNews;
	
	@Column(name="title")
	private String title;
	
	@Column(name="snews")
	private String snews;
	
	@UpdateTimestamp
	@Column(name="date_created")
	private Timestamp dateCreated;
		
}
