package com.cwt.bpg.cbt.tpromigration.ldap.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class LDAPConnection {
	
	@Value("${com.cwt.ldap.url}")
    private String url;

    @Value("${com.cwt.ldap.base}")
    private String base;

    @Value("${com.cwt.ldap.principal}")
    private String principal;

    @Value("${com.cwt.ldap.credentials}")
    private String credentials;
    
    
    @Bean
    public LdapContextSource ldapSource() {
    	
        final LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(url);
        contextSource.setBase(base);
        contextSource.setUserDn(principal);
        contextSource.setPassword(credentials);
        contextSource.afterPropertiesSet();
        
        return contextSource;
    }

}
