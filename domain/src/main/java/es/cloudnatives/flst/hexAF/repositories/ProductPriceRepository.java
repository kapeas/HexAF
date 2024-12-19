package es.cloudnatives.flst.hexAF.repositories;

import es.cloudnatives.flst.hexAF.model.ProductPriceAtGivenDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPriceAtGivenDate, Integer> {

    @Query("SELECT p FROM Prices p WHERE :paramDate between p.start and p.end and p.productId = :paramProductId and p.brandId = :paramBrand ORDER BY p.priority DESC LIMIT 1")
    ProductPriceAtGivenDate getProductPriceAtGivenDate(@Param("paramDate") Timestamp timestamp, @Param("paramProductId") Integer productId, @Param("paramBrand")Integer brandId);

    @Query("SELECT p FROM Prices p")
    Collection<ProductPriceAtGivenDate> getAllProdutPrices();

    //VULNERABLE QUERY. Testing...
    @NativeQuery("SELECT * FROM Prices WHERE DESCRIPTION LIKE %:vulnParamValue% ")
    Collection<ProductPriceAtGivenDate> getVulnerableResults(String vulnParamValue);
}
