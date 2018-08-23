package com.cwt.bpg.cbt.tpromigration.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cwt.bpg.cbt.tpromigration.service.MigrationService;

public class Application {

	public static void main(String[] args) throws Exception {
		
		System.setProperty("spring.profiles.default","dev");
		
		String countryCode = doCountryCodeSelection();
		
		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");) {
			
			MigrationService service = context.getBean(MigrationService.class);
			//service.setCountryCode(countryCode);
			
			/*if ("IN".equalsIgnoreCase(countryCode)) {
				service.migrateAirlineRules();
				service.migrateAirports();
				service.migrateClients();
			}
			else {
				service.migrateMerchantFees();
				service.migrateRemarks();
			}
			
			service.migrateProductList();*/

			//service.migratePassthroughs();
		}
	}

	private static String doCountryCodeSelection() throws IOException {

		List<String> countryCodes = new ArrayList<>();
		countryCodes.add("SG");
		countryCodes.add("HK");
		countryCodes.add("IN");
		System.out.println("Select Country Code: " + countryCodes);
		try (Scanner scanner = new Scanner(System.in)) {
			String countryCode = scanner.nextLine().toUpperCase();
			if (countryCodes.contains(countryCode)) {
				System.setProperty("country.code", countryCode.toUpperCase());
				System.out.println("Country Code: " + countryCode.toUpperCase());
				return countryCode;
			}
			else {
				System.out.println("Invalid input!");
				doCountryCodeSelection();
			}
		}
		return null;
	}
}
