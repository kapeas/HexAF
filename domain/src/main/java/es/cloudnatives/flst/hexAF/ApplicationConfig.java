package es.cloudnatives.flst.hexAF;

import es.cloudnatives.flst.hexAF.services.ProductPriceService;
import es.cloudnatives.flst.hexAF.services.impl.ProductPriceServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean(name="productsServiceBean")
    public ProductPriceService getProductsService(){
        return new ProductPriceServiceImpl();
    }

}
