package com.jb.news.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jb.news.model.News;

@Repository
public interface INews extends CrudRepository<News, Integer> {
	
	@Query("SELECT n FROM News n WHERE n.title LIKE :title||'%'")
	public List<News> findByTitle(@Param("title") String title);

}
