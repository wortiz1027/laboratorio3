package co.edu.javeriana.apiintermediaterouting.repositories;


import co.edu.javeriana.apiintermediaterouting.model.IntermediateRouting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface IntermediateRoutingRepository extends JpaRepository<IntermediateRouting, BigDecimal> {
    @Query("SELECT rou FROM IntermediateRouting rou WHERE rou.nombreServicio = :nombreServicio")
    public IntermediateRouting getbyName(@Param("nombreServicio") String nombreServicio);
}