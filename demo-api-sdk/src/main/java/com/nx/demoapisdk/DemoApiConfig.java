package com.nx.demoapisdk;

import com.nx.demoapisdk.client.DemoApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * demo api configuration
 *
 * @author nx-xn2002
 * @date 2025-01-22
 */
@Configuration
@ConfigurationProperties("demo.api")
@Data
@ComponentScan
public class DemoApiConfig {
    private String accessKey;
    private String secretKey;

    @Bean
    public DemoApiClient demoApiClient() {
        return new DemoApiClient(accessKey, secretKey);
    }
}
