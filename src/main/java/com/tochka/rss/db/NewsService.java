package com.tochka.rss.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tochka.rss.db.repo.NewsRepository;
import com.tochka.rss.domain.News;

@Service
public class NewsService {
	private final NewsRepository repo;

	@Autowired
	public NewsService(NewsRepository repo) {
		this.repo = repo;
	}

	public List<News> findAll() {
		return repo.findAll();
	}

	public Page<News> findPage(Pageable pageable) {
		return repo.findAll(pageable);
	}

	public Page<News> findPageByTitle(Pageable pageable, String title) {
		return repo.findAllByTitle(pageable, title);
	}

	public News save(News news) {
		repo.save(news);
		return news;
	}

	public void saveOrUpdate(News notPersistentNews) {
		News persistentNews = repo.findByLink(notPersistentNews.getLink());
		if (persistentNews == null) {
			// save new
			save(notPersistentNews);
		} else {
			// update, because we already have persistent news
			save(persistentNews);
		}
	}
}
