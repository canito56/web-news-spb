package com.jb.news.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="news")
public class News {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_news;
	@Column(name="title")
	private String title;
	@Column(name="snews")
	private String snews;
	@UpdateTimestamp
	@Column(name="date_created")
	private Timestamp date_created;
	
	public News() {
		
	}

	public News(int id_news, String title, String snews, Timestamp date_created) {
		this.id_news = id_news;
		this.title = title;
		this.snews = snews;
		this.date_created = date_created;
	}

	public int getId_news() {
		return id_news;
	}

	public void setId_news(int id_news) {
		this.id_news = id_news;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSnews() {
		return snews;
	}

	public void setSnews(String snews) {
		this.snews = snews;
	}

	public Timestamp getDate_created() {
		return date_created;
	}

	public void setDate_created(Timestamp date_created) {
		this.date_created = date_created;
	}

	@Override
	public String toString() {
		return "News [id_news=" + id_news + ", title=" + title + ", snews=" + snews + ", date_created=" + date_created
				+ "]";
	}
	
}
