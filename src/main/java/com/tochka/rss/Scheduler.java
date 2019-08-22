package com.tochka.rss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tochka.rss.service.ProcessingURL;

@Component
public class Scheduler {
	private ProcessingURL procService;
	
	@Autowired
	public Scheduler(ProcessingURL procService) {
		this.procService = procService;
	}
	@Scheduled(cron = "0 0/2 * * *  ?")
	public void updateNews() {
		procService.updateNews();
	}

}
