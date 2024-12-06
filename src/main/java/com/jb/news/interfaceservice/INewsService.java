package com.jb.news.interfaceservice;

import java.util.List;
import java.util.Optional;

import com.jb.news.model.News;

public interface INewsService {
	public List<News> list(String title);
	public Optional<News> listId(int id);
	public void updateNews(News n);
	public void deleteNews(int id);
}
