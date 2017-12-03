package com.bridgelabz.todoApp.scheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bridgelabz.todoApp.notes.service.NoteService;

@Component
public class TrashScheduler {
	
	/*@Autowired 
	NoteService noteService;
	
	Logger logger = Logger.getLogger(TrashScheduler.class);
	
	@Scheduled(fixedDelay = 60000)
	public void scheduleFixedDelayTask() {
	    	      
		logger.info("********Thrashing garbage");
		
		int deleted = 0;
	    
	    // call trashing from noteService
		deleted = noteService.deleteTrash();
		
		logger.info(deleted+" notes deleted");
	    
	}*/

}
