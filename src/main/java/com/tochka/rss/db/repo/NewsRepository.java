package com.tochka.rss.db.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.tochka.rss.domain.News;

public interface NewsRepository extends PagingAndSortingRepository<News, Long> {

	List<News> findAll();

	News findByLink(String link);	
	
	@Query("SELECT n FROM News n WHERE UPPER(n.title) LIKE CONCAT('%',UPPER(:title),'%')")
	Page<News> findAllByTitle(Pageable pageable, @Param("title") String title);
	
	//i can write easier, but i want to take experience with @Query
	//Page<News> findAllByTitleLike(Pageable pageable, String username);
}
