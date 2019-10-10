package com.cwt.bpg.cbt.air.transaction;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionBackup;
import com.cwt.bpg.cbt.service.CommonBackupService;

@Service
public class AirTransactionBackupService extends CommonBackupService<AirTransaction, AirTransactionBackup>
{
    @Autowired
    public AirTransactionBackupService(AirTransactionRepository repository, AirTransactionBackupRepository backupRepository)
    {
        super(repository, backupRepository);
    }

    @Override
    public List<AirTransactionBackup> createBackupList(List<AirTransaction> toBackup, Instant dateTime, String batchId)
    {
        final List<AirTransactionBackup> airTransactionBackupList = new ArrayList<>();
        toBackup.forEach(airTransaction -> {
            AirTransactionBackup backup = new AirTransactionBackup();
            backup.setBatchId(batchId);
            backup.setDateCreated(Instant.now());
            backup.setAirTransaction(airTransaction);
            airTransactionBackupList.add(backup);
        });
        return airTransactionBackupList;

    }
}
