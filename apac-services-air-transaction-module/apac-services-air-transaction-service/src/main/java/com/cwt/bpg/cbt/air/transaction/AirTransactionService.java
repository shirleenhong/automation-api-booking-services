package com.cwt.bpg.cbt.air.transaction;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cwt.bpg.cbt.air.transaction.exception.AirTransactionNoContentException;
import com.cwt.bpg.cbt.air.transaction.file.reader.AirTransExcelReader;
import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionInput;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionOutput;
import com.cwt.bpg.cbt.air.transaction.model.PassthroughType;
import com.cwt.bpg.cbt.upload.model.CollectionGroup;

@Service
public class AirTransactionService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AirTransactionService.class);

    private static final String EXCEL_WORKBOOK = "xlsx";

    @Autowired
    private AirTransactionRepository airTransactionRepo;

    @Autowired
    private AirTransactionGroupService airTransGroupService;

    @Autowired
    private AirTransExcelReader excelReader;

    public AirTransactionOutput getAirTransaction(AirTransactionInput input) throws AirTransactionNoContentException
    {
        CollectionGroup collectionGroup = airTransGroupService.getActiveCollectionGroup();
        input.setGroupId(collectionGroup.getGroupId());

        List<AirTransaction> airTransactionList = getAirTransactionList(input);
        checkEmptyList(airTransactionList);

        Optional<AirTransaction> passthroughCWT = airTransactionList.stream()
                .filter(p -> PassthroughType.CWT.equals(p.getPassthroughType()))
                .findFirst();

        return passthroughCWT.isPresent() ? createPassthroughOutput(PassthroughType.CWT)
                : createPassthroughOutput(PassthroughType.AIRLINE);
    }

    public List<AirTransaction> getAirTransactionList(AirTransactionInput input)
    {
        CollectionGroup collectionGroup = airTransGroupService.getActiveCollectionGroup();
        input.setGroupId(collectionGroup.getGroupId());
        return airTransactionRepo.getAirTransactions(input);
    }

    private void checkEmptyList(List<AirTransaction> airTransactionList)
            throws AirTransactionNoContentException
    {

        if (ObjectUtils.isEmpty(airTransactionList))
        {
            throw new AirTransactionNoContentException("AirTransaction data not found.");
        }
    }

    private AirTransactionOutput createPassthroughOutput(PassthroughType type)
    {
        AirTransactionOutput output = new AirTransactionOutput();
        output.setPassthroughType(type);
        return output;
    }

    public List<AirTransaction> save(List<AirTransaction> airTrans)
    {
        CollectionGroup collectionGroup = airTransGroupService.getActiveCollectionGroup();
        airTrans.forEach(a -> a.setGroupId(collectionGroup.getGroupId()));

        return StreamSupport.stream(airTransactionRepo.putAll(airTrans).spliterator(), false).collect(Collectors.toList());
    }

    public AirTransaction save(AirTransaction airTrans)
    {
        CollectionGroup collectionGroup = airTransGroupService.getActiveCollectionGroup();
        airTrans.setGroupId(collectionGroup.getGroupId());

        return airTransactionRepo.put(airTrans);
    }

    public String delete(String id)
    {
        return airTransactionRepo.remove(new ObjectId(id));
    }

    public void upload(InputStream inputStream, String fileType)
    {
        if (EXCEL_WORKBOOK.equalsIgnoreCase(fileType))
        {
            try
            {
                final List<AirTransaction> airTransactions = excelReader.parse(new BufferedInputStream(inputStream));
                airTransGroupService.save(airTransactions);
            }
            catch (Exception e)
            {
                LOGGER.error("Error in uploading Air Transactions: {}", e.getMessage());
            }
        }
        else
        {
            throw new IllegalArgumentException("File must be in excel format");
        }
    }
}
