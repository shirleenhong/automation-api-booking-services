package com.cwt.bpg.cbt.tpromigration.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cwt.bpg.cbt.tpromigration.service.MigrationService;

public class Application {
	
	
	public static void main(String[] args) {
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		try(ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");)
		{
			
			MigrationService service = context.getBean(MigrationService.class);
			
			service.startMigration();
		}
		
	}
}
