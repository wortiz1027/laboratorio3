package co.edu.javeriana.apiintermediaterouting.repositories;


import co.edu.javeriana.apiintermediaterouting.model.IntermediateRouting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface IntermediateRoutingRepository extends JpaRepository<IntermediateRouting, BigDecimal> {
    
}