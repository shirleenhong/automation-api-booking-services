package com.cwt.bpg.cbt.tpromigration.mssqldb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.cwt.bpg.cbt.tpromigration.encryptor.Encryptor;

@Configuration("com.cwt.bpg.cbt.tpromigration.mssqldb.config")
public class MssqlDBConfiguration {

    @Autowired
    private Encryptor encryptor;
    
    @Value("${com.cwt.tprodb.host}")//com.cwt.tprodb.host=localhost\\SQLEXPRESS
    private String dbHost;
    @Value("${com.cwt.tprodb.port}")//com.cwt.tprodb.port=1433
    private String dbPort;
    @Value("${com.cwt.tprodb.dbname}")//com.cwt.tprodb.dbname=TProSG_local
    private String dbName;
    @Value("${com.cwt.tprodb.dbuser}")//com.cwt.tprodb.dbuser=ZEayOfMPeH3tni24l2hvFw==
    private String dbuser;
    @Value("${com.cwt.tprodb.dbpwd}")//com.cwt.tprodb.dbpwd=EDVhB6NgoPGJUrVAADbIzw==
    private String dbPwd;

	@Bean
	public DriverManagerDataSource dataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dataSource.setUrl("jdbc:sqlserver://"+dbHost+":"+dbPort+";databaseName="+ dbName);
		dataSource.setUsername(encryptor.decrypt(dbuser));
		dataSource.setPassword(encryptor.decrypt(dbPwd));
		return dataSource;
	}
}
