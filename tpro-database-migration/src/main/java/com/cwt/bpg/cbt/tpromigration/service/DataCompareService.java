package com.cwt.bpg.cbt.tpromigration.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.tpromigration.mongodb.config.MongoDbConnection;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.ClientDAOImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;

@Service
public class DataCompareService {
	
	private static final Logger logger = LoggerFactory.getLogger(DataCompareService.class);
	
	@Autowired
    private MongoDbConnection mongoDbConnection;
	
    @Autowired
    private ClientDAOImpl clientDAO;
	
	public void compareMongoClients(File clientAccountNumberFile) {
		try {
			List<String> readLines = FileUtils.readLines(clientAccountNumberFile, StandardCharsets.UTF_8);

			logger.info("Getting mongodb clients...");
			// get mongo clients
			BasicDBObject inQuery = new BasicDBObject();
			inQuery.put("clientAccountNumber", new BasicDBObject("$in", readLines));
			FindIterable<Document> find = mongoDbConnection.getCollection("clients")
					.find(inQuery);

			final List<String> mongoClients = new ArrayList<>();
			find.forEach(new Block<Document>() {
				@Override
				public void apply(Document t) {
					mongoClients.add((String) t.get("clientAccountNumber"));
				}
			});

			// compare file list vs mongo hits
			readLines.forEach(e -> {
				boolean anyMatch = mongoClients.stream().anyMatch(m -> m.equals(e));
				final String match = anyMatch ? "OK!" : "<----------- MISSING ----------->";
				logger.info("{}\t{}", e, match);
			});

		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void compareMiddlesareClients(File clientAccountNumberFile) {
		try {
			List<String> readLines = FileUtils.readLines(clientAccountNumberFile, StandardCharsets.UTF_8);

			List<String> mwClientAccountNumber = clientDAO.getMWClientAccountNumber(readLines);

			// compare file list vs sql hits
			readLines.forEach(e -> {
				boolean anyMatch = mwClientAccountNumber.stream().anyMatch(m -> m.equals(e));
				final String match = anyMatch ? "OK!" : "<----------- MISSING ----------->";
				logger.info("{}\t{}", e, match);
			});
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
