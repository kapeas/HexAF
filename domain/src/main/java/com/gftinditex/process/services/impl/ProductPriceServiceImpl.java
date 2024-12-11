package com.gftinditex.process.services.impl;

import com.gftinditex.process.model.PrecioEnFecha;
import com.gftinditex.process.repositories.ProductPriceRepository;
import com.gftinditex.process.services.ProductPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ProductPriceServiceImpl implements ProductPriceService {

    Logger logger = LoggerFactory.getLogger(ProductPriceServiceImpl.class);

    @Autowired
    private ProductPriceRepository repo;

    @Override
    public PrecioEnFecha obtenerPrecioArticuloPorFecha(OffsetDateTime fecha, Integer articulo, Integer marca) {
        logger.info(">> obtenerPrecioArticuloPorFecha");
        Date date = Date.from(fecha.toInstant());
        Timestamp ts = Timestamp.from(date.toInstant());

        PrecioEnFecha precioEnFechaRespuesta = repo.obtenerPrecio(ts, articulo, marca);

        logger.info("<< obtenerPrecioArticuloPorFecha");
        return precioEnFechaRespuesta;
    }

    @Override
    public List<PrecioEnFecha> obtenerTodosPrecios() {
        Collection<PrecioEnFecha> tempRes = repo.obtenerTodosLosPrecios();
        logger.info("TempRes tiene " + tempRes.size() + " registros");

        Collection<PrecioEnFecha> tempRes2 = repo.findAll();
        logger.info("TempRes2 tiene " + tempRes.size() + " registros");
        return (List<PrecioEnFecha>)tempRes;
    }
}
