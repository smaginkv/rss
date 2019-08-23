package com.tochka.rss.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tochka.rss.db.repo.NewsURL_Repository;
import com.tochka.rss.domain.NewsURL;

@Service
public class NewsURL_Service {
	private NewsURL_Repository repository;

	@Autowired
	public NewsURL_Service(NewsURL_Repository repository) {
		this.repository = repository;
	}

	public NewsURL save(NewsURL newsURL) {
		repository.save(newsURL);
		return newsURL;
	}

	public List<NewsURL> findAll() {
		return repository.findAll();
	}
}
