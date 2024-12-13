package es.cloudnatives.flst.hexAF.rest;

import es.cloudnatives.flst.generated.application.model.ProductsGet200Response;
import es.cloudnatives.flst.generated.domain.ApiException;
import es.cloudnatives.flst.generated.domain.api.DefaultApi;
import es.cloudnatives.flst.generated.domain.model.ProductosGet200Response;
import es.cloudnatives.generated.domain.api.ProductsApi;
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
public class PriceByDateController implements ProductsApi {

    Logger logger = LoggerFactory.getLogger(PriceByDateController.class);


    @Autowired
    private DefaultApi api;


    public ResponseEntity<ProductsGet200Response> productosGet(OffsetDateTime fecha, Integer articulo, Integer marca) {

        logger.info(">> Application PriceByDateController productosGet");

        ProductsGet200Response response = new ProductsGet200Response();


        ProductsGet200Response clientResponse = new ProductsGet200Response();
        try {
            clientResponse = api.productosGet(fecha, articulo, marca);
            logger.info(clientResponse.toString());
            mapClientResptToServerResp(response, clientResponse);
            return ResponseEntity.ok(response);
        } catch (ApiException e) {

            //Establecer los parámetros recibidos.
            clientResponse.setProductId(articulo);
            clientResponse.setRequestedDate(fecha);
            clientResponse.setBrand(marca);

            int code = e.getCode();

            try {
                JSONParser errorResponseJson = new JSONParser(e.getResponseBody());
                clientResponse.setBackendMessage(errorResponseJson.object().get("mensaje").toString());
            } catch (ParseException ex) {
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

    private static void mapClientResptToServerResp(ProductsGet200Response response, ProductsGet200Response clientResponse) {
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
        return ProductsApi.super.getRequest();
    }
}
