package co.edu.javeriana.apiconvenios.repositories;

import co.edu.javeriana.apiconvenios.model.Convenio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ConveniosRepository extends JpaRepository<Convenio, BigDecimal> {

}
