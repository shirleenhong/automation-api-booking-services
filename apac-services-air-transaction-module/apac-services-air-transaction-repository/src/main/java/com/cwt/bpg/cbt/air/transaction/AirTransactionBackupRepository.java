package com.cwt.bpg.cbt.air.transaction;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.air.transaction.model.AirTransactionBackup;
import com.cwt.bpg.cbt.repository.CommonBackupRepository;

@Repository
public class AirTransactionBackupRepository extends CommonBackupRepository<AirTransactionBackup, ObjectId>
{
    private static final String KEY_COLUMN = "id";

    public AirTransactionBackupRepository()
    {
        super(AirTransactionBackup.class, KEY_COLUMN);
    }
}
