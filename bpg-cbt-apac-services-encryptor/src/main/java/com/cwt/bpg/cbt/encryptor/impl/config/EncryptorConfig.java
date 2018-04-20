package com.cwt.bpg.cbt.encryptor.impl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import com.cwt.bpg.cbt.encryptor.impl.Encryptor;

@Configuration("com.cwt.bpg.cbt.encryptor.impl.config.encryptor")
@Import({ PropertyConfig.class })
public class EncryptorConfig
{
    @Autowired
    private Environment env;

    @Bean
    public Encryptor encryptor()
    {
        return new Encryptor(env.getRequiredProperty("encryptor.secret.key"));
    }
}
