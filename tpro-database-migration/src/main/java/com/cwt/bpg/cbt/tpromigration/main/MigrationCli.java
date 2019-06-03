package com.cwt.bpg.cbt.tpromigration.main;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cwt.bpg.cbt.tpromigration.service.MigrationService;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(mixinStandardHelpOptions = true, version = "TPro data migration tool")
public class MigrationCli implements Callable<Void> {
	
	@Option(names = "-environment", required = true, description = "dev, test, preprod")
	private String environment;
	
	@Option(names = "-country", required = true, description = "availables is HK, SG, IN")
	private String countryCode;
	
	@Option(names = "-in-airline-rules", description = "migrate india airline rules")
	private boolean migrateAirlineRules;
	
	@Option(names = "-in-airports", description = "migrate india airports")
	private boolean migrateAirports;
	
	@Option(names = "-in-clients", description = "migrate india clients")
	private boolean migrateClients;
	
	@Option(names = "-line-definition-client-mapping", description = "migrate line definition client mapping")
	private boolean migrateLineDefinitionClientMapping;
	
	
	/**
	 * HK & SG
	 */
	@Option(names = "-air-contacts", description = "migrate HK & SG air contacts")
	private boolean migrateAirContacts;
	
	@Option(names = "-product-list", description = "migrate HK & SG products")
	private boolean migrateProductList;
	
	@Option(names = "-agent-contacts", description = "migrate HK & SG agent contacts")
	private boolean migrateAgentContacts;
	
	@Option(names = "-remarks", description = "migrate HK & SG remarks")
	private boolean migrateRemarks;
	
	@Option(names = "-merchant-fee", description = "migrate HK & SG merchange fees")
	private boolean migrateMerchantFees;
	
	private static List<String> countryCodes = Arrays.asList("IN", "HK", "SG");
	
	public static void main(String[] args) {
		new CommandLine(new MigrationCli()).execute(args);
	}
	
	@Override
	public Void call() throws Exception {
		if (!countryCodes.contains(countryCode.toUpperCase())) {
			System.out.println("Country is not supported.");
			return null;
		}
		System.setProperty("country.code", countryCode.toUpperCase());
		System.setProperty("spring.profiles.active", environment);
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		MigrationService service = context.getBean(MigrationService.class);
		service.setCountryCode(countryCode);
				
		if(migrateLineDefinitionClientMapping) {
			service.getAllLineDefs();
		}
		
		System.out.println("Country: " + countryCode);
		System.out.println("Rules: " + migrateAirlineRules);
		System.out.println("airports: " + migrateAirports);
		
		context.close();
		return null;
	}
}
