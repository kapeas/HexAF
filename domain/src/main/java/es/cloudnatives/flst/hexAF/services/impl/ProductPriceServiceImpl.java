package es.cloudnatives.flst.hexAF.services.impl;

import es.cloudnatives.flst.hexAF.model.ProductPriceAtGivenDate;
import es.cloudnatives.flst.hexAF.repositories.ProductPriceRepository;
import es.cloudnatives.flst.hexAF.services.ProductPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

public class ProductPriceServiceImpl implements ProductPriceService {

    Logger logger = LoggerFactory.getLogger(ProductPriceServiceImpl.class);

    @Autowired
    private ProductPriceRepository repo;


    @Override
    public List<ProductPriceAtGivenDate> getAllItemPricesByDate() {
        return List.of();
    }


    @Override
    public ProductPriceAtGivenDate getItemPriceByDate(OffsetDateTime dateTime, Integer productId, Integer brandId) {
        logger.info(">> getItemPriceByDate");
        Date date = Date.from(dateTime.toInstant());
        Timestamp ts = Timestamp.from(date.toInstant());

        ProductPriceAtGivenDate productPriceAtGivenDateResp = repo.getProductPriceAtGivenDate(ts, productId, brandId);

        logger.info("<< getItemPriceByDate");
        return productPriceAtGivenDateResp;
    }
}
