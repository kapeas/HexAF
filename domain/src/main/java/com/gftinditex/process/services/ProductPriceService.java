package com.gftinditex.process.services;

import com.gftinditex.process.model.PrecioEnFecha;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

import java.time.LocalDate;

@Service
public interface ProductPriceService {

    PrecioEnFecha obtenerPrecioArticuloPorFecha(OffsetDateTime fecha, Integer articulo, Integer marca);
    List<PrecioEnFecha> obtenerTodosPrecios();

}

