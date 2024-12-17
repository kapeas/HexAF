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
            logger.error("APP!!!!Domain api exception response body: "+ responseBody);
            logger.error("En CATCH APIEXCEPTION. clientResponse.backend_msg: "+ clientResponse.getBackendMessage());

            JSONParser errorResponseJson = new JSONParser(responseBody);
            logger.error("después del parse error response");
            response.setBackendMessage(clientResponse.getBackendMessage());
            logger.error("después del setbackendmsg:55");
            try {

                //show all errorResponseJsonKeys
                logger.error("antes del  error response get object");

                LinkedHashMap<String, Object> object = errorResponseJson.object();
                logger.error("después del  error response get object");
//                Set<String> keysInJson = object.keySet();
//
//                for (String k : keysInJson) {
//                    logger.error("IN CATCH APIEX. SHOW ALL KEYS . Key: " + k + ". Value: " + errorResponseJson.object().get(k));
//                }
//
                logger.error("object.get(\"backend_message\") is: " + object.get("backend_message"));
                logger.error("object.get(\"backend_message\").toString() is: " + );

                response.setBackendMessage(object.get("backend_message").toString());
                clientResponse.setBackendMessage(object.get("backend_message").toString());
                int code = e.getCode();
                logger.error("antes del response.setBackendMessage(\"Appl");

                response.setBackendMessage("Application Pending domain msg. failing previous set backend msg line 49");
                logger.error("despues del response.setBackendMessage(\"Appl");
                response.setProductId(productId);

                logger.error("antes del response.setRequestedDate(\"Appl");
                response.setRequestedDate(dateTime != null ? dateTime : OffsetDateTime.now());
                logger.error("después del response.setRequestedDate(\"Appl");

                logger.error("DENTRO DEL TRY: El valor de backendmsg recibido de domain en application es" + clientResponse.getBackendMessage());

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
//                logger.error(e.getCause().toString()); //cause null :/
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
