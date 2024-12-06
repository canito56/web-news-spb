package com.jb.news.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jb.news.interfaces.INews;
import com.jb.news.interfaceservice.INewsService;
import com.jb.news.model.News;

@Service
public class NewsService implements INewsService {

	private INews data;
	
	public NewsService(INews data) {
		this.data = data;
	}
	
	public List<News> list(String title) { 
		if (title.equals("")) {
			return (List<News>) data.findAll();
		}
		return data.findByTitle(title);		
	}

	public Optional<News> listId(int id) {
		return data.findById(id);
	}

	public void updateNews(News n) {
		data.save(n);
	}	
	
	public void deleteNews(int id) {
		data.deleteById(id);
	}

}
