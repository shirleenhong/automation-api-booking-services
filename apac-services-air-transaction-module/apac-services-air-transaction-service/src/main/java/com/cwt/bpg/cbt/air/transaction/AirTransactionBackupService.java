package com.cwt.bpg.cbt.air.transaction;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionBackup;

@Service
public class AirTransactionBackupService
{
    @Autowired
    private AirTransactionRepository repository;
    
    @Autowired
    private AirTransactionBackupRepository backupRepository;
    
    public void archive() {
        
        List<AirTransaction> toArchive = repository.getAll();
        
        if(!CollectionUtils.isEmpty(toArchive)) {
            
            List<AirTransactionBackup> airTranBackups = new ArrayList<>();
            
            for(AirTransaction airTran : toArchive) {
                
                AirTransactionBackup backup = new AirTransactionBackup();
                Instant currentDateTime = Instant.now();
                
                backup.setDateCreated(currentDateTime);
                backup.setAirTransaction(airTran);
                
                airTranBackups.add(backup);
            }
            
            backupRepository.putAll(airTranBackups);
        }
    }
}
