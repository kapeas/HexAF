package com.gftinditex.process.repositories;

import com.gftinditex.process.model.PrecioEnFecha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;

@Repository
public interface ProductPriceRepository extends JpaRepository<PrecioEnFecha, Integer> {

    @Query("SELECT p FROM Prices p WHERE :paramFecha between p.start and p.end and p.productId = :paramArticulo and p.brandId = :paramMarca ORDER BY p.priority DESC LIMIT 1")
    PrecioEnFecha obtenerPrecio(@Param("paramFecha") Timestamp fecha, @Param("paramArticulo") Integer articulo, @Param("paramMarca")Integer marca);

    @Query("SELECT p FROM Prices p")
    Collection<PrecioEnFecha> obtenerTodosLosPrecios();
}
