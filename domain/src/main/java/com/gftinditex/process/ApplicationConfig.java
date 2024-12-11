package com.gftinditex.process;

import com.gftinditex.process.services.ProductPriceService;
import com.gftinditex.process.services.impl.ProductPriceServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean(name="productosServiceBean")
    public ProductPriceService getServicioProductos(){
        return new ProductPriceServiceImpl();
    }


}
