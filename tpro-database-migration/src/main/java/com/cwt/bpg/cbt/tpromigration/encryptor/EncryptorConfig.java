package com.cwt.bpg.cbt.tpromigration.encryptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("com.cwt.bpg.cbt.tpromigration.encryptor.config")
public class EncryptorConfig
{
    @Value("${com.cwt.tpromigration.encryptor.key}")
    private String secretKey;

    @Bean
    public Encryptor encryptor()
    {
        return new Encryptor(secretKey);
    }
}
