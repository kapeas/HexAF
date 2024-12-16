package es.cloudnatives.flst.hexAF.rest;

import com.gftinditex.adapterserver.api.AdapterProductsApi;
import com.gftinditex.adapterserver.model.AdapterProductsGet200Response;
import com.gftinditex.generatedadapterclient.ApiException;
import com.gftinditex.generatedadapterclient.api.DefaultApi;
import com.gftinditex.generatedadapterclient.model.ApplicationProductsGet200Response;
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
public class PriceByDateController implements AdapterProductsApi {

    Logger logger = LoggerFactory.getLogger(PriceByDateController.class);
    @Autowired
    private DefaultApi api;

    @Override
    public ResponseEntity<AdapterProductsGet200Response> adapterProductsGet(OffsetDateTime dateTime, Integer product, Integer brand) {

        logger.info(">> Adapter PriceByDateController productsGet %s %s %s", dateTime, product, brand);

        AdapterProductsGet200Response response = new AdapterProductsGet200Response(); //server 200 response
        ApplicationProductsGet200Response clientResponse = new ApplicationProductsGet200Response(); //application client response 200

        try {
            clientResponse = api.applicationProductsGet(dateTime, product, brand);
        } catch (ApiException e) {
            clientResponse.setProductId(product);
            clientResponse.setRequestedDate(dateTime);
            clientResponse.setBrand(brand);
            int code = e.getCode();

            try {
                JSONParser errorResponseJson = new JSONParser(e.getResponseBody());
                clientResponse.setBackendMessage(errorResponseJson.object().get("backend_message").toString());
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }

            return switch (code) {
                case 404 -> {
                    mapClientRespToServerResp(response, clientResponse);
                    yield ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
                case 400 -> {

                    mapClientRespToServerResp(response, clientResponse);
                    yield ResponseEntity.badRequest().body(response);
                }
                default -> throw new RuntimeException(e);
            };

        }
        logger.info("<< Adapter PriceByDateController productsGet %s %s %s", dateTime, product, brand);
        return mapClientRespToServerResp(response, clientResponse);
    }

    private static ResponseEntity<AdapterProductsGet200Response> mapClientRespToServerResp(AdapterProductsGet200Response response, ApplicationProductsGet200Response clientResponse) {
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
