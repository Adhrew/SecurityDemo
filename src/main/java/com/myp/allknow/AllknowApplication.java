package com.myp.allknow;

import com.myp.allknow.configure.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@EnableConfigurationProperties(RsaKeyProperties.class)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class AllknowApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllknowApplication.class, args);
    }

}
