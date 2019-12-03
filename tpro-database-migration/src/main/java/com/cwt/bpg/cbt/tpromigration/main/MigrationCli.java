package com.cwt.bpg.cbt.tpromigration.main;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cwt.bpg.cbt.tpromigration.service.DataCompareService;
import com.cwt.bpg.cbt.tpromigration.service.MigrationService;

import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(mixinStandardHelpOptions = true, version = "TPro data migration tool")
public class MigrationCli implements Callable<Void>
{

    private static final Logger logger = LoggerFactory.getLogger(MigrationCli.class);

    @Option(names = "-environment", required = true, description = "dev, int, preprod")
    private String environment;

    @Option(names = "-country", required = true, description = "HK, SG, IN")
    private String countryCode;

    @ArgGroup(validate = false, heading = "%noptions for india migration%n")
    private IndiaOptions indiaOptions;

    @ArgGroup(validate = false, heading = "%noptions for Singapore / Hong Kong migration%n")
    private HkSgOptions hkSgOptions;

    @ArgGroup(validate = false, heading = "%ncompare migration client, applicable for india only%n")
    private ClientCompareOptions clientCompareOptions;

    private static List<String> countryCodes = Arrays.asList("IN", "HK", "SG");

    public static void main(String[] args)
    {
        CommandLine commandLine = new CommandLine(new MigrationCli());
        commandLine.execute(args);
    }

    @Override
    public Void call() throws Exception
    {
        if (!countryCodes.contains(countryCode.toUpperCase()))
        {
            logger.warn("Country is not supported.");
            return null;
        }
        System.setProperty("country.code", countryCode.toUpperCase());
        System.setProperty("spring.profiles.active", environment);
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        MigrationService service = context.getBean(MigrationService.class);
        DataCompareService dataCompareService = context.getBean(DataCompareService.class);
        service.setCountryCode(countryCode);

        logger.info("Country code: {}", countryCode);

        if (countryCode.equalsIgnoreCase("IN"))
        {
            if (indiaOptions != null)
            {
               if (indiaOptions.migrateLineDefinitionClientMapping)
               {
                   logger.info("Migrating line definition client mapping...");
                   service.migrateLineDefClientMapping();
               }
               if (indiaOptions.migrateAirlineRules)
               {
                   logger.info("Migrating airline rules...");
                   service.migrateAirlineRules();
               }
               if (indiaOptions.migrateClients)
               {
                   logger.info("Migrating clients...");
                   service.migrateClients();
               }
               if (indiaOptions.clientTransactionFees)
               {
                   logger.info("Migrating client transaction fees...");
                   service.migrateClientTransactionFees();
               }
               if (indiaOptions.migrateAirports)
               {
                   logger.info("Migrating airports...");
                   service.migrateAirports();
               }
               if (indiaOptions.migrateAirMiscInfo)
               {
                   logger.info("Migrating air misc info...");
                   if (StringUtils.isNotBlank(indiaOptions.wave))
                   {
                       service.migrateAirMiscInfoMerged(indiaOptions.wave);
                   }
                   else
                   {
                       service.migrateAirMiscInfo();
                   }
               }
               if (indiaOptions.migratePassthroughs)
               {
                   logger.info("Migrating passthru's...");
                   if (StringUtils.isNotBlank(indiaOptions.wave))
                   {
                       service.migratePassthroughs(indiaOptions.wave);
                   }
               }
            }

            if (clientCompareOptions != null)
            {
                if (clientCompareOptions.compareMongodbClients) {
                    if (clientCompareOptions.clientAccountNumberFile == null)
                    {
                        logger.error("No input file, please provide input file.");
                    }
                    else
                    {
                        logger.info("Comparing list to mongodb clients.");
                        dataCompareService.compareMongoClients(clientCompareOptions.clientAccountNumberFile);
                    }
                }
                if (clientCompareOptions.compareMiddlewareClients)
                {
                    if (clientCompareOptions.clientAccountNumberFile == null)
                    {
                        logger.error("No input file, please provide input file.");
                    }
                    else
                    {
                        logger.info("Comparing list to middleware clients.");
                        dataCompareService.compareMiddlesareClients(clientCompareOptions.clientAccountNumberFile);
                    }
                }
            }
        }
        if (countryCode.equalsIgnoreCase("HK") || countryCode.equalsIgnoreCase("SG"))
        {
            if (hkSgOptions != null)
            {
                 if (hkSgOptions.migrateAirContacts)
                 {
                     logger.info("Migrating air contacts...");
                     service.migrateAirContracts();
                 }
                 if (hkSgOptions.migrateMerchantFees)
                 {
                     logger.info("Migrating merchant fees...");
                     service.migrateMerchantFees();
                 }
                 if (hkSgOptions.migrateAgentContacts)
                 {
                     logger.info("Migrating agent contacts...");
                     service.migrateAgentContacts();
                 }
                 if (hkSgOptions.migrateProductList)
                 {
                     logger.info("Migrating product list...");
                     service.migrateProductList();
                 }
            }
        }
        context.close();
        return null;
    }

    private static class IndiaOptions {
        @Option(names = "-in-airline-rules", description = "migrate india airline rules")
        boolean migrateAirlineRules;

        @Option(names = "-in-airports", description = "migrate india airports")
        boolean migrateAirports;

        @Option(names = "-in-client-transaction-fees", description = "migrate india client transaction fees")
        boolean clientTransactionFees;

        @Option(names = "-in-clients", description = "migrate india clients")
        boolean migrateClients;

        @Option(names = "-in-air-misc-info", description = "migrate india air misc info")
        boolean migrateAirMiscInfo;

        @Option(names = "-in-pass-thru", description = "migrate india pass throughs")
        boolean migratePassthroughs;

        @Option(
                names = "-line-definition-client-mapping",
                description = "migrate line definition client mapping")
        boolean migrateLineDefinitionClientMapping;

        @Option(names = "-wave", description = "wave migration folder")
        String wave;
    }

    private static class HkSgOptions {
        @Option(names = "-air-contacts", description = "migrate HK & SG air contacts")
        boolean migrateAirContacts;

        @Option(names = "-product-list", description = "migrate products")
        boolean migrateProductList;

        @Option(names = "-agent-contacts", description = "migrate HK & SG agent contacts")
        boolean migrateAgentContacts;

        @Option(names = "-remarks", description = "migrate HK & SG remarks")
        boolean migrateRemarks;

        @Option(names = "-merchant-fee", description = "migrate HK & SG merchant fees")
        boolean migrateMerchantFees;
    }

    private static class ClientCompareOptions {
        @Option(names = "-compare-mongodb-clients", description = "compare client account numbers from file to mongodb clients")
        boolean compareMongodbClients;

        @Option(names = "-compare-middleware-clients", description = "compare client account numbers from file to middleware clients")
        boolean compareMiddlewareClients;

        @Option(names = "--file", paramLabel = "FILE", description = "file that contains a list of client account numbers")
        File clientAccountNumberFile;
    }
}
