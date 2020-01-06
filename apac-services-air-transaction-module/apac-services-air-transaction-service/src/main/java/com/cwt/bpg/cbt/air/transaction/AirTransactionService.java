package com.cwt.bpg.cbt.air.transaction;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cwt.bpg.cbt.air.transaction.exception.AirTransactionNoContentException;
import com.cwt.bpg.cbt.air.transaction.exception.AirTransactionUploadException;
import com.cwt.bpg.cbt.air.transaction.file.reader.AirTransExcelReader;
import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionInput;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionOutput;
import com.cwt.bpg.cbt.air.transaction.model.PassthroughType;
import com.cwt.bpg.cbt.upload.model.CollectionGroup;

@Service
public class AirTransactionService
{
    private static final String EXCEL_WORKBOOK = "xlsx";

    @Autowired
    private AirTransactionRepository airTransactionRepository;

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
        return airTransactionRepository.getAirTransactions(input);
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

    public List<AirTransaction> save(List<AirTransaction> airTransaction)
    {
        CollectionGroup collectionGroup = airTransGroupService.getActiveCollectionGroup();
        airTransaction.forEach(a -> a.setGroupId(collectionGroup.getGroupId()));

        return StreamSupport.stream(airTransactionRepository.putAll(airTransaction).spliterator(), false).collect(Collectors.toList());
    }

    public AirTransaction save(AirTransaction airTrans)
    {
        CollectionGroup collectionGroup = airTransGroupService.getActiveCollectionGroup();
        airTrans.setGroupId(collectionGroup.getGroupId());

        return airTransactionRepository.put(airTrans);
    }

    public String delete(String id)
    {
        return airTransactionRepository.remove(new ObjectId(id));
    }

    public void upload(InputStream inputStream, String fileType) throws AirTransactionUploadException
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
                throw new AirTransactionUploadException("Error in uploading air transaction from file", e);
            }
        }
        else
        {
            throw new IllegalArgumentException("File must be in excel format");
        }
    }
}
