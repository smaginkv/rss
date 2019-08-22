package com.tochka.rss.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tochka.rss.db.NewsService;
import com.tochka.rss.domain.News;
import com.tochka.rss.service.ProcessingURL;

@Controller
public class NewsController {
	private NewsService newsRepo;
	private ProcessingURL procService;

	@Autowired
	public NewsController(NewsService newsRepo, ProcessingURL procService) {
		this.newsRepo = newsRepo;
		this.procService = procService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getNewsListForm(Model model, Pageable pageable) {
		Pageable newPageable = PageRequest.of(pageable.getPageNumber(), 10);
		Page<News> page = newsRepo.findPage(newPageable);
		model.addAttribute("page", page);
		return "news";
	}

	@RequestMapping(value = "/", params = { "refresh_news" }, method = RequestMethod.GET)
	public String updateNews() {
		procService.updateNews();
		return "redirect:/";
	}

	@RequestMapping(value = "/", params = { "search_by_title" }, method = RequestMethod.GET)
	public String searchNews(Model model, Pageable pageable, @RequestParam("title") String title) {
		Pageable newPageable = PageRequest.of(pageable.getPageNumber(), 10);
		Page<News> page = newsRepo.findPageByTitle(newPageable, title);
		model.addAttribute("page", page);
		return "news";
	}
}
