package com.gftinditex.process.rest;

import com.gftinditex.api.ProductosApi;
import com.gftinditex.model.ProductosGet200Response;
import com.gftinditex.process.model.PrecioEnFecha;
import com.gftinditex.process.services.ProductPriceService;
import com.gftinditex.process.services.impl.ProductPriceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;


@RestController
public class PriceByDateController implements ProductosApi {

    Logger logger = LoggerFactory.getLogger(ProductPriceServiceImpl.class);

    @Autowired
    private ProductPriceService service;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ProductosApi.super.getRequest();
    }


    @Override
    public ResponseEntity<ProductosGet200Response> productosGet(OffsetDateTime fecha, Integer articulo, Integer marca) {
    
        logger.info(">> domain controller");
        PrecioEnFecha precioEnFecha = new PrecioEnFecha();


        if (fecha == null) {
            logger.error("Fecha null.");
            ProductosGet200Response response = mapRecordToRespone(fecha, articulo, marca, precioEnFecha);
            response.setMensaje("El parámetro fecha es obligatorio y debe tener un formato correcto (YYYY-MM-DDTHH:mm:ssZ).");
            return ResponseEntity.badRequest().body(response);
        }

        if (articulo == null) {
            logger.error("articulo null.");
            ProductosGet200Response response = mapRecordToRespone(fecha, articulo, marca, precioEnFecha);
            response.setMensaje("El parámetro articulo es obligatorio y debe ser un entero.");
            return ResponseEntity.badRequest().body(response);
        }

        if (marca == null) {
            logger.error("marca null");
            ProductosGet200Response response = mapRecordToRespone(fecha, articulo, marca, precioEnFecha);
            response.setMensaje("El parámetro marca es obligatorio y debe ser un entero.");
            return ResponseEntity.badRequest().body(response);
        }

        //TODO: Añadir cualquier lógica de negocio aquí, en caso de ser necesario, para personalizar el comportamiento.

        precioEnFecha = service.obtenerPrecioArticuloPorFecha(fecha, articulo, marca);

        if (precioEnFecha == null || precioEnFecha.getPrice() == null || precioEnFecha.getPrice() == 0f){
            //No se ha obtenido ningún registro para los parámetros proporcionados, aunque esos parámetros tengan un formato válido.
            //TODO: Este cast huele mal....
            precioEnFecha = new PrecioEnFecha();
            String mensaje = "No se han encontrado registros de precios para los parámetros proporcionados";
            logger.error(mensaje);
//            final ProductosGet200Response response = new ProductosGet200Response();
            ProductosGet200Response response = mapRecordToRespone(fecha, articulo, marca, precioEnFecha);
            response.setMensaje(mensaje);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        logger.info(precioEnFecha.getCurrency());
        logger.info(precioEnFecha.toString());

        ProductosGet200Response response = mapRecordToRespone(fecha, articulo, marca, precioEnFecha);
        logger.info("<< domain controller");
        return ResponseEntity.ok(response);
    }


    /**
     * Se encarga de mapear un registro obtenido de la base de datos a un objeto válido para la respuesta.
     * @param fecha
     * @param articulo
     * @param marca
     * @param precioEnFecha
     * @return
     */
    private static ProductosGet200Response mapRecordToRespone(OffsetDateTime fecha, Integer articulo, Integer marca, PrecioEnFecha precioEnFecha) {
        ProductosGet200Response response = new ProductosGet200Response();
        response.setFechaConsultada(fecha);
        response.setMarca(marca != null ? marca : -1);
        response.setProductId(articulo != null ? articulo: -1);

        long millisSinceEpochStart = precioEnFecha.getStart() != null ? precioEnFecha.getStart().getTime() : 0;
        long millisSinceEpochEnd = precioEnFecha.getEnd() != null ? precioEnFecha.getEnd().getTime() : 0;

        Instant instantStart = Instant.ofEpochMilli(millisSinceEpochStart);
        Instant instantEnd = Instant.ofEpochMilli(millisSinceEpochEnd);
        OffsetDateTime dateTimeStart = OffsetDateTime.ofInstant(instantStart, ZoneId.of("UTC"));
        OffsetDateTime dateTimeEnd = OffsetDateTime.ofInstant(instantEnd, ZoneId.of("UTC"));

        response.setFechaInicioCampania(dateTimeStart);
        response.setFechaFinCampania(dateTimeEnd);
        response.setPrecio(precioEnFecha.getPrice());
        response.setOrdenPrecio(precioEnFecha.getPrice_list_order());
        response.setPrioridad(precioEnFecha.getPriority());
        response.setDivisa(precioEnFecha.getCurrency());
        return response;
    }

}
