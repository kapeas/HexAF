package es.cloudnatives.flst.hexAF.services;

import es.cloudnatives.flst.hexAF.model.ProductPriceAtGivenDate;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public interface ProductPriceService {

    ProductPriceAtGivenDate getItemPriceByDate(OffsetDateTime dateTime, Integer productId, Integer brandId);
    List<ProductPriceAtGivenDate> getAllItemPricesByDate();

}

