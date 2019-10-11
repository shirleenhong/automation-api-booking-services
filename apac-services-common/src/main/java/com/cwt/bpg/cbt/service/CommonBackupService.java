package com.cwt.bpg.cbt.service;

import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;

import org.mongodb.morphia.query.FindOptions;

import com.cwt.bpg.cbt.repository.CommonBackupRepository;
import com.cwt.bpg.cbt.repository.CommonRepository;
import com.mongodb.CommandResult;

public abstract class CommonBackupService<O, B> {

	private static final int BATCH_SIZE = 1000;
	private static final String COUNT_STAT = "count";

	private CommonRepository<O, ?> repository;

	private CommonBackupRepository<B, ?> backupRepository;

	public CommonBackupService(CommonRepository<O, ?> repository, CommonBackupRepository<B, ?> backupRepository) {
		this.repository = repository;
		this.backupRepository = backupRepository;
	}

	public void archive(List<O> sourceList, String batchId) {
		archive(sourceList, batchId, false);
	}

	public void archive(List<O> sourceList, String batchId, boolean removeBackup) {
		CommandResult stats = repository.getStats();
		Integer size = (Integer) stats.get(COUNT_STAT);
		if (size == null || size == 0) {
			return; // size will be null if collection doesn't exist
		}
		if (removeBackup) {
			backupRepository.dropCollection();
		}
		Instant currentDateTime = Instant.now();
		int batches = (int) Math.ceil(size / (double) BATCH_SIZE);
		int currentBatch = 0;
		int skip = 0;
		int limit = BATCH_SIZE;
		while (++currentBatch <= batches) {
			FindOptions options = new FindOptions().skip(skip).limit(limit);
			List<O> toBackup = repository.getAll(options);
			sourceList.addAll(toBackup);
			List<B> backups = createBackupList(toBackup, currentDateTime, batchId);
			backupRepository.putAll(backups);
			skip = skip + limit;
			limit = limit + BATCH_SIZE;
		}
		repository.dropCollection();
	}

	public abstract List<B> createBackupList(List<O> toBackup, Instant dateTime, String batchId);

	public void rollback(List<O> backupList, String batchId) {
		backupRepository.removeBatchBackup(batchId);
		if (backupList != null && !backupList.isEmpty()) {
			backupList.stream().forEach(updateBackup());
			repository.putAll(backupList);
		}
	}

	/**
	 * Override to set id to null if needed
	 */
	public Consumer<? super O> updateBackup() {
		return e -> {
		};
	}

}
