package es.cloudnatives.flst.hexAF.config;


import es.cloudnatives.flst.generatedadapterclient.ApiClient;
import es.cloudnatives.flst.generatedadapterclient.api.DefaultApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean(name="applicationApiClientBean")
    public DefaultApi apiClient() {
        return new DefaultApi(
                new ApiClient().setHost("app").setPort(8081)
        );
    }
}
