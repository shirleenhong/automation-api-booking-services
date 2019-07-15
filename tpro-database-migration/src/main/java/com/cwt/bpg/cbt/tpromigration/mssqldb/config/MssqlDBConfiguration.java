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
    
    @Value("${com.cwt.tprodb.host}")
    private String dbHost;
    @Value("${com.cwt.tprodb.port}")
    private String dbPort;
    @Value("${com.cwt.tprodb.dbname}")
    private String dbName;
	@Value("${com.cwt.tprodb.dbuser}")
    private String dbuser;
    @Value("${com.cwt.tprodb.dbpwd}")
    private String dbPwd;
    
    @Value("${com.cwt.middlewaredb.host}")
    private String middlewareDbHost;
    @Value("${com.cwt.middlewaredb.port}")
    private String middlewareDbPort;
    @Value("${com.cwt.middlewaredb.dbname}")
    private String middlewareDbName;
	@Value("${com.cwt.middlewaredb.dbuser}")
    private String middlewareDbuser;
    @Value("${com.cwt.middlewaredb.dbpwd}")
    private String middlewareDbPwd;

	@Bean
	public DriverManagerDataSource dataSource() throws Exception
    {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dataSource.setUrl("jdbc:sqlserver://"+dbHost+":"+dbPort+";databaseName="+ dbName);
		dataSource.setUsername(encryptor.decrypt(dbuser));
		dataSource.setPassword(encryptor.decrypt(dbPwd));
		return dataSource;
	}
	
	@Bean
	public DriverManagerDataSource middlewareDataSource() throws Exception
    {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dataSource.setUrl("jdbc:sqlserver://"+middlewareDbHost+":"+middlewareDbPort+";databaseName="+ middlewareDbName);
		dataSource.setUsername(encryptor.decrypt(middlewareDbuser));
		dataSource.setPassword(encryptor.decrypt(middlewareDbPwd));
		return dataSource;
	}
}
