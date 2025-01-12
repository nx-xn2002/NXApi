package com.nx.nxapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * knife4j 配置文件
 *
 * @author Ni Xiang
 * @date 2025-01-06
 */
@Configuration
public class Knife4jConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("NXApi - API")
                        .contact(new Contact()
                                .name("Ni Xiang"))
                        .version("1.0")
                        .description("api doc for dev environment"));
    }

}
