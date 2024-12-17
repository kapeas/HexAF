package es.cloudnatives.flst.hexAF.rest;

import es.cloudnatives.flst.hexAF.model.ProductPriceAtGivenDate;
import es.cloudnatives.flst.hexAF.services.ProductPriceService;
import es.cloudnatives.flst.hexAF.services.impl.ProductPriceServiceImpl;
import es.cloudnatives.generated.domain.api.DomainProductsApi;
//import es.cloudnatives.generated.domain.model.DomainProductsGet200Response;
import es.cloudnatives.generated.domain.model.GetProductPrice200Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;


@RestController
public class PriceByDateController implements DomainProductsApi {

    Logger logger = LoggerFactory.getLogger(ProductPriceServiceImpl.class);

    @Autowired
    private ProductPriceService service;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return DomainProductsApi.super.getRequest();
    }


    @Override
    public ResponseEntity<GetProductPrice200Response> getProductPrice(OffsetDateTime dateTime, Integer productId, Integer brand) {
        logger.info(">> domain controller");
        ProductPriceAtGivenDate productPriceAtGivenDate = new ProductPriceAtGivenDate();


        if (dateTime == null) {
            logger.error("dateTime null.");
            GetProductPrice200Response response = mapRecordToRespone(dateTime, productId, brand, productPriceAtGivenDate);
            response.setBackendMessage("Param dateTime is mandatory and requires the following format: (YYYY-MM-DDTHH:mm:ssZ).");
            return ResponseEntity.badRequest().body(response);
        }

        if (productId == null) {
            logger.error("productId null.");
            GetProductPrice200Response response = mapRecordToRespone(dateTime, productId, brand, productPriceAtGivenDate);
            response.setBackendMessage("Param productId is mandatory and requires the following format: (Integer number).");
            return ResponseEntity.badRequest().body(response);
        }

        if (brand == null) {
            logger.error("marca null");
            GetProductPrice200Response response = mapRecordToRespone(dateTime, productId, brand, productPriceAtGivenDate);
            response.setBackendMessage("Param brand is mandatory and requires the following format: (Integer number)");
            return ResponseEntity.badRequest().body(response);
        }

        productPriceAtGivenDate = service.getItemPriceByDate(dateTime, productId, brand);

        if (productPriceAtGivenDate == null || productPriceAtGivenDate.getPrice() == null || productPriceAtGivenDate.getPrice() == 0f){
            productPriceAtGivenDate = new ProductPriceAtGivenDate();
            String noRecordsFoundsErrorMsg = "No itemPriceAtGivenTime records were found with the given params.";
            logger.error(noRecordsFoundsErrorMsg);
            GetProductPrice200Response response = mapRecordToRespone(dateTime, productId, brand, productPriceAtGivenDate);
            response.setBackendMessage(noRecordsFoundsErrorMsg);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        logger.info(productPriceAtGivenDate.getCurrency());
        logger.info(productPriceAtGivenDate.toString());

        GetProductPrice200Response response = mapRecordToRespone(dateTime, productId, brand, productPriceAtGivenDate);
        logger.info("<< domain controller");
        return ResponseEntity.ok(response);
    }


    private static GetProductPrice200Response mapRecordToRespone(OffsetDateTime dateTime, Integer productId, Integer brand, ProductPriceAtGivenDate productPriceAtGivenDate) {
        GetProductPrice200Response response = new GetProductPrice200Response();
        response.setRequestedDate(dateTime != null ? dateTime : OffsetDateTime.now());
        response.setBrand(brand != null ? brand : -1);
        response.setProductId(productId != null ? productId: -1);

        long millisSinceEpochStart = productPriceAtGivenDate.getStart() != null ? productPriceAtGivenDate.getStart().getTime() : 0;
        long millisSinceEpochEnd = productPriceAtGivenDate.getEnd() != null ? productPriceAtGivenDate.getEnd().getTime() : 0;

        Instant instantStart = Instant.ofEpochMilli(millisSinceEpochStart);
        Instant instantEnd = Instant.ofEpochMilli(millisSinceEpochEnd);
        OffsetDateTime dateTimeStart = OffsetDateTime.ofInstant(instantStart, ZoneId.of("UTC"));
        OffsetDateTime dateTimeEnd = OffsetDateTime.ofInstant(instantEnd, ZoneId.of("UTC"));

        response.setCampaignStartDate(dateTimeStart);
        response.setCampaignEndDate(dateTimeEnd);
        response.setPrice(productPriceAtGivenDate.getPrice());
        response.setPriceListOrder(productPriceAtGivenDate.getPrice_list_order());
        response.setPriority(productPriceAtGivenDate.getPriority());
        response.setCurrency(productPriceAtGivenDate.getCurrency());
        return response;
    }

}
