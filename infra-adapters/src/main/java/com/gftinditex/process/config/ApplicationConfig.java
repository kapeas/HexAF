package com.gftinditex.process.config;


import com.gftinditex.generatedadapterclient.ApiClient;
import com.gftinditex.generatedadapterclient.api.DefaultApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean(name="domainApiClientBean")
    public DefaultApi apiClient() {
        return new DefaultApi(
                new ApiClient().setHost("app").setPort(8081)
        );
    }
}
