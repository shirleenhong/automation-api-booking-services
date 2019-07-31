package com.cwt.bpg.cbt.tpromigration.main;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cwt.bpg.cbt.tpromigration.service.MigrationService;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(mixinStandardHelpOptions = true, version = "TPro data migration tool")
public class MigrationCli implements Callable<Void>
{

    private static final Logger LOGGER = LoggerFactory.getLogger(MigrationCli.class);

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

    @Option(names = "-in-air-misc-info", description = "migrate india air misc info")
    private boolean migrateAirMiscInfo;

    @Option(names = "-in-pass-thru", description = "migrate india pass throughs")
    private boolean migratePassthroughs;

    @Option(names = "-line-definition-client-mapping", description = "migrate line definition client mapping")
    private boolean migrateLineDefinitionClientMapping;

    @Option(names = "-wave", description = "wave migration folder")
    private String wave;

    /**
     * HK & SG
     */
    @Option(names = "-air-contacts", description = "migrate HK & SG air contacts")
    private boolean migrateAirContacts;

    @Option(names = "-product-list", description = "migrate products")
    private boolean migrateProductList;

    @Option(names = "-agent-contacts", description = "migrate HK & SG agent contacts")
    private boolean migrateAgentContacts;

    @Option(names = "-remarks", description = "migrate HK & SG remarks")
    private boolean migrateRemarks;

    @Option(names = "-merchant-fee", description = "migrate HK & SG merchange fees")
    private boolean migrateMerchantFees;

    private static List<String> countryCodes = Arrays.asList("IN", "HK", "SG");

    public static void main(String[] args)
    {
        new CommandLine(new MigrationCli()).execute(args);
    }

    @Override
    public Void call() throws Exception
    {
        if (!countryCodes.contains(countryCode.toUpperCase()))
        {
            LOGGER.warn("Country is not supported.");
            return null;
        }
        System.setProperty("country.code", countryCode.toUpperCase());
        System.setProperty("spring.profiles.active", environment);
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        MigrationService service = context.getBean(MigrationService.class);
        service.setCountryCode(countryCode);

        LOGGER.info("Country code: {}", countryCode);

        if (countryCode.equalsIgnoreCase("IN"))
        {
            if (migrateLineDefinitionClientMapping)
            {
                LOGGER.info("Migrating line definition client mapping...");
                service.migrateLineDefClientMapping();
            }
            if (migrateAirlineRules)
            {
                LOGGER.info("Migrating airline rules...");
                service.migrateAirlineRules();
            }
            if (migrateClients)
            {
                LOGGER.info("Migrating clients...");
                service.migrateClients();
            }
            if (migrateAirports)
            {
                LOGGER.info("Migrating airports...");
                service.migrateAirports();
            }
            if (migrateAirMiscInfo)
            {
                LOGGER.info("Migrating air misc info...");
                if (StringUtils.isNotBlank(wave))
                {
                    service.migrateAirMiscInfoMerged(wave);
                }
                else
                {
                    service.migrateAirMiscInfo();
                }
            }
            if (migratePassthroughs)
            {
                LOGGER.info("Migrating passthru's...");
                if (StringUtils.isNotBlank(wave))
                {
                    service.migratePassthroughs(wave);
                }
            }
        }
        if (countryCode.equalsIgnoreCase("HK") || countryCode.equalsIgnoreCase("SG"))
        {
            if (migrateAirContacts)
            {
                LOGGER.info("Migrating air contacts...");
                service.migrateAirContracts();
            }
            if (migrateMerchantFees)
            {
                LOGGER.info("Migrating merchant fees...");
                service.migrateMerchantFees();
            }
            if (migrateAgentContacts)
            {
                LOGGER.info("Migrating agent contacts...");
                service.migrateAgentContacts();
            }
        }

        if (migrateProductList)
        {
            LOGGER.info("Migrating product list...");
            service.migrateProductList();
        }

        context.close();
        return null;
    }
}
