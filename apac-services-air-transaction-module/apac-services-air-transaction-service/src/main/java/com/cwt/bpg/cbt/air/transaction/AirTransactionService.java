package com.cwt.bpg.cbt.air.transaction;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cwt.bpg.cbt.air.transaction.exception.AirTransactionBackupException;
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
                .filter(p -> PassthroughType.CWT.equals(p.getPassthroughType()))
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

    public List<AirTransaction> getAirTransactionList(AirTransactionInput input)
    {
        return airTransactionRepo.getAirTransactions(input);
    }

    public List<AirTransaction> save(List<AirTransaction> airTrans)
    {
        return StreamSupport.stream(airTransactionRepo.putAll(airTrans).spliterator(), false).collect(Collectors.toList());
    }

    public AirTransaction save(AirTransaction airTransaction)
    {
        return airTransactionRepo.put(airTransaction);
    }

    public String delete(String id)
    {
        return airTransactionRepo.remove(new ObjectId(id));
    }

    public void upload(InputStream inputStream, String fileType) throws AirTransactionBackupException
    {
        if (EXCEL_WORKBOOK.equalsIgnoreCase(fileType))
        {
            String batchId = UUID.randomUUID().toString();
            final List<AirTransaction> airTransactionList = new ArrayList<>();
            try
            {
                final List<AirTransaction> updatedList = excelReader.parse(new BufferedInputStream(inputStream));
                airTransBackupService.archive(airTransactionList, batchId);
                airTransactionRepo.putAll(updatedList);
            }
            catch (Exception e)
            {
                airTransBackupService.rollback(airTransactionList, batchId);
                LOGGER.error("Error in creating backup of air transaction from excel file", e);
                throw new AirTransactionBackupException("Error in creating backup of air transaction from excel file", e);
            }
        }
        else
        {
            throw new IllegalArgumentException("File must be in excel format");
        }
    }
}
