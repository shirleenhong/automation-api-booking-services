package com.cwt.bpg.cbt.air.transaction;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.air.transaction.model.AirTransactionBackup;
import com.cwt.bpg.cbt.repository.CommonRepository;

@Repository
public class AirTransactionBackupRepository extends CommonRepository<AirTransactionBackup, ObjectId>
{
    private static final String KEY_COLUMN = "id";

    public AirTransactionBackupRepository()
    {
        super(AirTransactionBackup.class, KEY_COLUMN);
    }
}
