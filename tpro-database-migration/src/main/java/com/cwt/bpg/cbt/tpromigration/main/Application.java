package com.cwt.bpg.cbt.tpromigration.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cwt.bpg.cbt.tpromigration.service.MigrationService;

public class Application {
	
	
	public static void main(String[] args) throws Exception {
		
		doCountryCodeSelection();
		try(ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");)
		{
			MigrationService service = context.getBean(MigrationService.class);
//			service.startMigration();
			service.migrateMerchantFees();
//			service.migrateCurrencies();
		}
	}

	private static void doCountryCodeSelection() {

		List<String> countryCodes = new ArrayList<>();
		countryCodes.add("SG");
		countryCodes.add("HK");
		countryCodes.add("IN");
		System.out.println("Select Country Code: " + countryCodes);
		try(Scanner scanner = new Scanner(System.in)){
			String countryCode = scanner.nextLine().toUpperCase();
			if(countryCodes.contains(countryCode)) {
		        System.setProperty("spring.profiles.default", countryCode);
				System.out.println("Country Code: " + System.getProperty("spring.profiles.default"));	
			}else{
				System.out.println("Invalid input!");
				doCountryCodeSelection() ;
			}
		}
	}
}
