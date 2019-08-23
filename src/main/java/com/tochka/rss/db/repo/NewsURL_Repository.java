package com.tochka.rss.db.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tochka.rss.domain.NewsURL;

public interface NewsURL_Repository extends CrudRepository<NewsURL, Long> {
	List<NewsURL> findAll();
}
