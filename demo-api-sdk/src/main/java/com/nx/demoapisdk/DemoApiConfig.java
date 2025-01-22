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
 */
@Configuration
@ConfigurationProperties("demo.api")
@Data
@ComponentScan
public class DemoApiConfig {
    /**
     * access key
     */
    private String accessKey;
    /**
     * secret key
     */
    private String secretKey;

    /**
     * demo api client
     *
     * @return {@link DemoApiClient }
     */
    @Bean
    public DemoApiClient demoApiClient() {
        return new DemoApiClient(accessKey, secretKey);
    }
}
