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
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Set;


@RestController
public class PriceByDateController implements ApplicationProductsApi {

    static Logger logger = LoggerFactory.getLogger(PriceByDateController.class);

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

            String responseBody = e.getResponseBody();
            logger.error("APP!!!!Domain api exception response body: "+ responseBody);
            JSONParser errorResponseJson = new JSONParser(responseBody);
            response.setBackendMessage(clientResponse.getBackendMessage());

            try {
                LinkedHashMap<String, Object> object = errorResponseJson.object();
                response.setBackendMessage(object.get("backend_message").toString());
                clientResponse.setBackendMessage(object.get("backend_message").toString());
                int code = e.getCode();
                response.setBackendMessage("Application Pending domain msg. failing previous set backend msg line 49");
                response.setProductId(productId);
                response.setRequestedDate(dateTime != null ? dateTime : OffsetDateTime.now());

                switch (code) {
                    case 404 -> {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapClientResptToServerResp(response,clientResponse));
                    }
                    case 400 -> {
                        return ResponseEntity.badRequest().body(mapClientResptToServerResp(response,clientResponse));
                    }
                    default -> throw new RuntimeException(e);
                }
            } catch (ParseException ex) {
                logger.error(e.getMessage());
                response.setBackendMessage("parse exception in application while consuming domain");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapClientResptToServerResp(response, clientResponse));
        }
    }

    private static GetProductPriceApplicationServer200Response mapClientResptToServerResp(GetProductPriceApplicationServer200Response response, GetProductPriceDomainClient200Response clientResponse) {
        response.setCurrency(clientResponse.getCurrency());
        response.setBrand(clientResponse.getBrand());
        logger.error(">>>>>>>>>>>>>>> Before setting backend message");
        logger.error("El valor de backendmsg recibido de domain en application es" + clientResponse.getBackendMessage());
        logger.error("El valor de backendmsg recibido de domain en application es" + clientResponse.getBackendMessage());
        logger.error("El valor de backendmsg recibido de domain en application es" + clientResponse.getBackendMessage());
        logger.error("El valor de backendmsg recibido de domain en application es" + clientResponse.getBackendMessage());
        logger.error("El valor de backendmsg recibido de domain en application es" + clientResponse.getBackendMessage());
        response.setBackendMessage(clientResponse.getBackendMessage() != null ? clientResponse.getBackendMessage() : null);
        logger.error("<<<<<<<<<<<<<<< After setting backend message");
        response.setPrice(clientResponse.getPrice());
        response.setPriority(clientResponse.getPriority());
        response.setRequestedDate(clientResponse.getRequestedDate() != null ? (clientResponse.getRequestedDate()) : null);
        response.setCampaignStartDate(clientResponse.getCampaignStartDate() != null ? clientResponse.getCampaignStartDate() : null);
        response.setCampaignEndDate(clientResponse.getCampaignEndDate() != null ? clientResponse.getCampaignEndDate() : null);
        response.setProductId(clientResponse.getProductId() != null ? clientResponse.getProductId() : null);
        response.setPriceListOrder(clientResponse.getPriceListOrder() != null ? clientResponse.getPriceListOrder() : null);
        return response;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ApplicationProductsApi.super.getRequest();
    }
}
