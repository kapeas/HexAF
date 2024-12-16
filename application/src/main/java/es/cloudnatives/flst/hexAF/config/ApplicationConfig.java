package es.cloudnatives.flst.hexAF.config;

import es.cloudnatives.generated.domain.ApiClient;
import es.cloudnatives.generated.domain.api.DefaultApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    //TODO: Remove config class if not needed for demo. Useless now.

    @Bean(name="domainApiClientBean")
    public DefaultApi apiClient() {
        return new DefaultApi(
                new ApiClient().setHost("domain").setPort(8082)
        );
    }
}
