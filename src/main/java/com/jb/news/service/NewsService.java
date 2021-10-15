package com.jb.news.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.news.interfaceService.INewsService;
import com.jb.news.interfaces.INews;
import com.jb.news.model.News;

@Service
public class NewsService implements INewsService {
	@Autowired
	private INews data;
	
	@Override
	public List<News> list(String title) { 
		if (title.equals(null) || title.equals("")) {
			return (List<News>) data.findAll();
		}
		return (List<News>) data.findByTitle(title);		
	}

	@Override
	public Optional<News> listId(int id) {
		return data.findById(id);
	}

	@Override
	public int addNews(News n) {
		int res = 0;
		News news = data.save(n);
		if (!news.equals(null)) {
			res = 1;
		}
		return res;
	}

	public int updateNews(News n) {
		int res = 0;
		News news = data.save(n);
		if (!news.equals(null)) {
			res = 1;
		}
		return res;
	}	
	
	@Override
	public void deleteNews(int id) {
		data.deleteById(id);
	}

}
