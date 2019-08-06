package com.cwt.bpg.cbt.air.transaction;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cwt.bpg.cbt.air.transaction.exception.AirTransactionNoContentException;
import com.cwt.bpg.cbt.air.transaction.file.reader.AirTransExcelReader;
import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionInput;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionOutput;
import com.cwt.bpg.cbt.air.transaction.model.PassthroughType;

@Service
public class AirTransactionService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AirTransactionService.class);

    private static final String EXCEL_WORKBOOK = "xlsx";

    @Resource
    private AirTransactionService proxy;

    @Autowired
    private AirTransactionRepository airTransactionRepo;

    @Autowired
    private AirTransExcelReader excelReader;
    
    @Autowired
    private AirTransactionBackupService airTransBackupService;

    public AirTransactionOutput getAirTransaction(AirTransactionInput input)
            throws AirTransactionNoContentException
    {

        List<AirTransaction> airTransactionList = proxy.getAirTransactionList(input);
        checkEmptyList(airTransactionList);

        Optional<AirTransaction> passthroughCWT = airTransactionList.stream()
                .filter(p -> p.getPassthroughType().equals(PassthroughType.CWT))
                .findFirst();

        return passthroughCWT.isPresent() ? createPassthroughOutput(PassthroughType.CWT)
                : createPassthroughOutput(PassthroughType.AIRLINE);
    }

    private AirTransactionOutput createPassthroughOutput(PassthroughType type)
    {
        AirTransactionOutput output = new AirTransactionOutput();
        output.setPassthroughType(type);
        return output;
    }

    private void checkEmptyList(List<AirTransaction> airTransactionList)
            throws AirTransactionNoContentException
    {

        if (ObjectUtils.isEmpty(airTransactionList))
        {
            throw new AirTransactionNoContentException("AirTransaction data not found.");
        }
    }

    @Cacheable(cacheNames = "air-transactions", key = "#input.countryCode + #input.airlineCode + #input.bookingClasses + #input.ccVendorCode")
    public List<AirTransaction> getAirTransactionList(AirTransactionInput input)
    {
        return airTransactionRepo.getAirTransactions(input);
    }

    public List<AirTransaction> save(List<AirTransaction> airTrans)
    {
        return StreamSupport.stream(airTransactionRepo.putAll(airTrans).spliterator(), false).collect(Collectors.toList());
    }

    public String delete(String id)
    {
        return airTransactionRepo.remove(new ObjectId(id));
    }

    public void upload(InputStream inputStream, String fileType)
    {
        try
        {
            if (EXCEL_WORKBOOK.equalsIgnoreCase(fileType))
            {
                List<AirTransaction> updatedList = excelReader.parse(new BufferedInputStream(inputStream));
                airTransBackupService.archive();
                airTransactionRepo.dropCollection();
                airTransactionRepo.putAll(updatedList);
            }
            else {
                throw new IllegalArgumentException("File must be in excel format");
            }
        }
        catch (IOException e)
        {
            LOGGER.error("Error in parsing of air transaction from excel file", e);
        }
    }
}
