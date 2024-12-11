package com.gftinditex.process.rest;

import com.gftinditex.adapterserver.api.ProductosApi;
import com.gftinditex.adapterserver.model.ProductosGet200Response;
import com.gftinditex.generatedcoreclient.ApiException;
import com.gftinditex.generatedcoreclient.api.DefaultApi;
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

    //TODO: Crear Servicios . Código desacoplado!!!!
    //TODO: Gestionar 400 y otros posibles errores.

    Logger logger = LoggerFactory.getLogger(PriceByDateController.class);
    @Autowired
    private DefaultApi api;

    public ResponseEntity<ProductosGet200Response> productosGet(OffsetDateTime fecha, Integer articulo, Integer marca) {

        logger.info(">> Application PriceByDateController productosGet");

        ProductosGet200Response response = new ProductosGet200Response(); //respuesta 200 DEL MODELO DEL SERVIDOR
        com.gftinditex.generatedcoreclient.model.ProductosGet200Response clientResponse = new com.gftinditex.generatedcoreclient.model.ProductosGet200Response(); //respuesta 200 DEL MODELO DEL CLIENTE (api que consume al dominio)

        try {
            clientResponse = api.productosGet(fecha, articulo, marca);
            logger.info(clientResponse.toString());
            mapClientResptToServerResp(response, clientResponse);
            return ResponseEntity.ok(response);
        } catch (ApiException e) {

            //Establecer los parámetros recibidos.
            clientResponse.setProductId(articulo);
            clientResponse.setFechaConsultada(fecha);
            clientResponse.setMarca(marca);

            int code = e.getCode();

            try {
                JSONParser errorResponseJson = new JSONParser(e.getResponseBody());
                clientResponse.setMensaje(errorResponseJson.object().get("mensaje").toString());
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

    private static void mapClientResptToServerResp(ProductosGet200Response response, com.gftinditex.generatedcoreclient.model.ProductosGet200Response clientResponse) {
        response.setDivisa(clientResponse.getDivisa());
        response.setMarca(clientResponse.getMarca());
        response.setMensaje(clientResponse.getMensaje() != null ? clientResponse.getMensaje() : null);
        response.setPrecio(clientResponse.getPrecio());
        response.setPrioridad(clientResponse.getPrioridad());
        response.setFechaConsultada(clientResponse.getFechaConsultada() != null ? (clientResponse.getFechaConsultada()) : null);
        response.setFechaInicioCampania(clientResponse.getFechaInicioCampania() != null ? clientResponse.getFechaInicioCampania() : null);
        response.setFechaFinCampania(clientResponse.getFechaFinCampania() != null ? clientResponse.getFechaFinCampania() : null);
        response.setProductId(clientResponse.getProductId() != null ? clientResponse.getProductId() : null);
        response.setOrdenPrecio(clientResponse.getOrdenPrecio() != null ? clientResponse.getOrdenPrecio() : null);
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ProductosApi.super.getRequest();
    }
}
