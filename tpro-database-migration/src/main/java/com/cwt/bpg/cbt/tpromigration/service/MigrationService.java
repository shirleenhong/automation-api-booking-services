package com.cwt.bpg.cbt.tpromigration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.tpromigration.encryptor.Encryptor;
import com.mongodb.MongoCredential;

@Service
public class MigrationService {

    @Autowired
    private Encryptor encryptor;

    @Value("${com.cwt.tpromigration.mongodb.dbuser}")
    private String dbUser;

    @Value("${com.cwt.tpromigration.mongodb.dbpwd}")
    private String dbPwd;

    @Value("${com.cwt.tpromigration.mongodb.dbname}")
    private String dbName;
    
	public void startMigration() {
		


        System.out.println(encryptor.decrypt(this.dbPwd));
        System.out.println(encryptor.decrypt(this.dbUser));
		System.out.println("start migration...");
	}
}
