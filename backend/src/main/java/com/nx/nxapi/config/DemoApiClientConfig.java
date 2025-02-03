package com.nx.nxapi.config;

import com.nx.demoapisdk.client.DemoApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * demo api client config
 *
 * @author nx-xn2002
 */
@Configuration
public class DemoApiClientConfig {
    @Value("${demo.api.access-key}")
    private String accessKey;
    @Value("${demo.api.secret-key}")
    private String secretKey;

    @Bean
    public DemoApiClient demoApiClient() {
        return new DemoApiClient(accessKey, secretKey);
    }
}
