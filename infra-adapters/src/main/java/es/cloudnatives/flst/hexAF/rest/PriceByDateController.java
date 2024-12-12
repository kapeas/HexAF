package es.cloudnatives.flst.hexAF.rest;

import com.gftinditex.adapterserver.api.ProductosApi;
import com.gftinditex.adapterserver.model.ProductosGet200Response;
import com.gftinditex.generatedadapterclient.ApiException;
import com.gftinditex.generatedadapterclient.api.DefaultApi;
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
public class PriceByDateController implements ProductosApi {

    Logger logger = LoggerFactory.getLogger(PriceByDateController.class);
    @Autowired
    private DefaultApi api;

    @Override
    public ResponseEntity<ProductosGet200Response> productosGet(OffsetDateTime fecha, Integer articulo, Integer marca) {
        logger.info(">> Adapter PriceByDateController productosGet %s %s %s", fecha, articulo, marca);

        ProductosGet200Response response = new ProductosGet200Response(); //respuesta 200 DEL MODELO DEL SERVIDOR
        com.gftinditex.generatedadapterclient.model.ProductosGet200Response clientResponse = new com.gftinditex.generatedadapterclient.model.ProductosGet200Response(); //respuesta 200 DEL MODELO DEL CLIENTE (api que consume al dominio)

        try {
            clientResponse = api.productosGet(fecha, articulo, marca);
        } catch (ApiException e) {
            //Establecer los parámetros recibidos.
            clientResponse.setProductId(articulo);
            clientResponse.setFechaConsultada(fecha);
            clientResponse.setMarca(marca);

            //TODO: Esta es la gestión correcta. modificar modelos de cada uno de los status code y verificar.
            int code = e.getCode();

            try {
                JSONParser errorResponseJson = new JSONParser(e.getResponseBody());
                clientResponse.setMensaje(errorResponseJson.object().get("mensaje").toString());
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
        logger.info("<< Adapter PriceByDateController productosGet %s %s %s", fecha, articulo, marca);
        return mapClientRespToServerResp(response, clientResponse);
    }

    private static ResponseEntity<ProductosGet200Response> mapClientRespToServerResp(ProductosGet200Response response, com.gftinditex.generatedadapterclient.model.ProductosGet200Response clientResponse) {
        response.setDivisa(clientResponse.getDivisa());
        response.setMarca(clientResponse.getMarca());
        response.setMensaje(clientResponse.getMensaje());
        response.setPrecio(clientResponse.getPrecio());
        response.setPrioridad(clientResponse.getPrioridad());
        response.setFechaConsultada(clientResponse.getFechaConsultada());
        response.setFechaInicioCampania(clientResponse.getFechaInicioCampania());
        response.setFechaFinCampania(clientResponse.getFechaFinCampania());
        response.setProductId(clientResponse.getProductId());
        response.setOrdenPrecio(clientResponse.getOrdenPrecio());
        return ResponseEntity.ok(response);
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ProductosApi.super.getRequest();
    }
}
