package com.cwt.bpg.cbt.repository;

import org.mongodb.morphia.query.Query;

public class CommonBackupRepository<T, D> extends CommonRepository<T, D> {
	
	public CommonBackupRepository(Class<T> typeClass, String keyColumn) {
		super(typeClass, keyColumn);
	}

	public boolean removeBatchBackup(String batchId) {
		Query<T> query = createQuery().field("batchId").equalIgnoreCase(batchId);
		return remove(query);
	}
}
