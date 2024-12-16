package es.cloudnatives.flst.hexAF.rest;

import es.cloudnatives.flst.generated.application.api.ApplicationProductsApi;
import es.cloudnatives.flst.generated.application.model.GetProductPriceApplicationServer200Response;
import es.cloudnatives.generated.domain.ApiException;
import es.cloudnatives.generated.domain.api.DefaultApi;
import es.cloudnatives.generated.domain.model.GetProductPriceDomainClient200Response;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.time.OffsetDateTime;
import java.util.Optional;


@RestController
public class PriceByDateController implements ApplicationProductsApi {

    Logger logger = LoggerFactory.getLogger(PriceByDateController.class);

    @Autowired
    private DefaultApi api;


    @Override
    public ResponseEntity<GetProductPriceApplicationServer200Response> getProductPriceApplicationServer(OffsetDateTime dateTime, Integer productId, Integer brand) {

        logger.info(">> Application PriceByDateController productosGet");

        GetProductPriceApplicationServer200Response response = new GetProductPriceApplicationServer200Response();


        GetProductPriceDomainClient200Response clientResponse = new GetProductPriceDomainClient200Response();
        try {
            clientResponse = api.getProductPriceDomainClient(dateTime, productId, brand);
            logger.info(clientResponse.toString());
            mapClientResptToServerResp(response, clientResponse);
            return ResponseEntity.ok(response);
        } catch (ApiException e) {

            //Establecer los parámetros recibidos.
            clientResponse.setProductId(productId);
            clientResponse.setRequestedDate(dateTime);
            clientResponse.setBrand(brand);

            int code = e.getCode();

            JSONParser errorResponseJson = new JSONParser(e.getResponseBody());
            try {
                if(errorResponseJson.object() != null && errorResponseJson.object().get("backend_message") != null) {
                    clientResponse.setBackendMessage(errorResponseJson.object().get("backend_message").toString());
                } else {
                    logger.error("DEBUG THIS. BACKEND MSG IN api exception, before switch");
                    logger.error("DEBUG THIS. BACKEND MSG IN api exception, before switch");
                    logger.error("DEBUG THIS. BACKEND MSG IN api exception, before switch");
                    logger.error("DEBUG THIS. BACKEND MSG IN api exception, before switch");
                    logger.error("DEBUG THIS. BACKEND MSG IN api exception, before switch");
                }

            } catch (ParseException ex) {

                logger.error("Parse Exception: " + ex.getMessage());
                logger.error("Parse Exception: " + ex.getCause());
                logger.error("Parse Exception: " + ex.getClass());
                e.printStackTrace();
                throw new RuntimeException(ex);
            }

            return switch (code) {
                case 404 -> {
                    mapClientResptToServerResp(response, clientResponse);
                    yield ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
                case 400 -> {
                    mapClientResptToServerResp(response, clientResponse);
                    yield ResponseEntity.badRequest().body(response);
                }
                default -> throw new RuntimeException(e);
            };
        }
    }

    private static void mapClientResptToServerResp(GetProductPriceApplicationServer200Response response, GetProductPriceDomainClient200Response clientResponse) {
        response.setCurrency(clientResponse.getCurrency());
        response.setBrand(clientResponse.getBrand());
        response.setBackendMessage(clientResponse.getBackendMessage() != null ? clientResponse.getBackendMessage() : null);
        response.setPrice(clientResponse.getPrice());
        response.setPriority(clientResponse.getPriority());
        response.setRequestedDate(clientResponse.getRequestedDate() != null ? (clientResponse.getRequestedDate()) : null);
        response.setCampaignStartDate(clientResponse.getCampaignStartDate() != null ? clientResponse.getCampaignStartDate() : null);
        response.setCampaignEndDate(clientResponse.getCampaignEndDate() != null ? clientResponse.getCampaignEndDate() : null);
        response.setProductId(clientResponse.getProductId() != null ? clientResponse.getProductId() : null);
        response.setPriceListOrder(clientResponse.getPriceListOrder() != null ? clientResponse.getPriceListOrder() : null);
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ApplicationProductsApi.super.getRequest();
    }
}
