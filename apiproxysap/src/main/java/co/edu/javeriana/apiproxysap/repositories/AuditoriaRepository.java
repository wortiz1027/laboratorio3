package co.edu.javeriana.apiproxysap.repositories;

import co.edu.javeriana.apiproxysap.model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, BigDecimal> {
}
