package co.edu.javeriana.apiconvenios.repositories;

import co.edu.javeriana.apiconvenios.model.Convenio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ConveniosRepository extends JpaRepository<Convenio, BigDecimal> {
}