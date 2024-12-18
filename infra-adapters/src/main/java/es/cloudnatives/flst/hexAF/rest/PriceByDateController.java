package es.cloudnatives.flst.hexAF.rest;

import com.gftinditex.adapterserver.api.AdapterProductsApi;
import com.gftinditex.adapterserver.model.GetProductPriceAdapterServer200Response;
import com.gftinditex.generatedadapterclient.ApiException;
import com.gftinditex.generatedadapterclient.api.DefaultApi;
import com.gftinditex.generatedadapterclient.model.GetProductPriceApplicationClient200Response;
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


@RestController
public class PriceByDateController implements AdapterProductsApi {

    Logger logger = LoggerFactory.getLogger(PriceByDateController.class);
    @Autowired
    private DefaultApi api;

    @Override
    public ResponseEntity<GetProductPriceAdapterServer200Response> getProductPriceAdapterServer(OffsetDateTime dateTime, Integer productId, Integer brand) {

        logger.info(">> Adapter PriceByDateController productsGet %s %s %s", dateTime, productId, brand);

        GetProductPriceAdapterServer200Response response = new GetProductPriceAdapterServer200Response(); //server 200 response
        GetProductPriceApplicationClient200Response clientResponse = new GetProductPriceApplicationClient200Response(); //application client response 200

        try {
            clientResponse = api.getProductPriceApplicationClient(dateTime, productId, brand);
        } catch (ApiException e) {
            String responseBody = e.getResponseBody();
            logger.error("ADAPTER!!!APPLICATION api exception response body: " + responseBody);
            JSONParser errorResponseJson = new JSONParser(responseBody);

            try {
                LinkedHashMap<String, Object> object = errorResponseJson.object();
                response.setBackendMessage(object.get("backend_message").toString());
                clientResponse.setBackendMessage(object.get("backend_message").toString());
                int code = e.getCode();
                response.setBackendMessage("Adapter Pending Application msg. failing previous set backend msg line 49");
                response.setProductId(productId);
                response.setRequestedDate(dateTime != null ? dateTime : OffsetDateTime.now());

                switch (code) {
                    case 404 -> {
                        logger.error("Adapter 404 error msg");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapClientRespToServerResp(response,clientResponse).getBody());
                    }
                    case 400 -> {
                        logger.error("Adapter 400 error msg");
                        return ResponseEntity.badRequest().body(mapClientRespToServerResp(response,clientResponse).getBody());
                    }
                    default -> throw new RuntimeException(e);
                }
            } catch (ParseException ex) {
                logger.error(e.getMessage());
                response.setBackendMessage("parse exception in application while consuming domain");
            }
            return mapClientRespToServerResp(response, clientResponse);
        }

        logger.info("<< Adapter PriceByDateController productsGet %s %s %s", dateTime, productId, brand);
        return mapClientRespToServerResp(response, clientResponse);
    }

    private static ResponseEntity<GetProductPriceAdapterServer200Response> mapClientRespToServerResp(GetProductPriceAdapterServer200Response response, GetProductPriceApplicationClient200Response clientResponse) {
        response.setCurrency(clientResponse.getCurrency());
        response.setBrand(clientResponse.getBrand());
        response.setBackendMessage(clientResponse.getBackendMessage());
        response.setPrice(clientResponse.getPrice());
        response.setPriority(clientResponse.getPriority());
        response.setRequestedDate(clientResponse.getRequestedDate());
        response.setCampaignStartDate(clientResponse.getCampaignStartDate());
        response.setCampaignEndDate(clientResponse.getCampaignEndDate());
        response.setProductId(clientResponse.getProductId());
        response.setPriceListOrder(clientResponse.getPriceListOrder());
        return ResponseEntity.ok(response);
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return AdapterProductsApi.super.getRequest();
    }
}
