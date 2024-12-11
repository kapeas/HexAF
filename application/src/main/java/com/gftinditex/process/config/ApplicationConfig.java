package com.gftinditex.process.config;

import com.gftinditex.generatedcoreclient.ApiClient;
import com.gftinditex.generatedcoreclient.api.DefaultApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfig {

    @Bean(name="domainApiClientBean")
    public DefaultApi apiClient() {
        return new DefaultApi(
                new ApiClient().setHost("domain").setPort(8082)
        );
    }
}
