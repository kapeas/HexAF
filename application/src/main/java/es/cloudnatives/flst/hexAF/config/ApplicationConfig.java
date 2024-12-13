package es.cloudnatives.flst.hexAF.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    //Disabled. Not implementing client from oas3 spec. Using domain generated artifact instead.
    //TODO: Remove config class if not needed for demo. Useless now.
//
//    @Bean(name="domainApiClientBean")
//    public DefaultApi apiClient() {
//        return new DefaultApi(
//                new ApiClient().setHost("domain").setPort(8082)
//        );
//    }
}
