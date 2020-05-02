package co.edu.javeriana.apiproveedores.repositories;

import co.edu.javeriana.apiproveedores.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProveedoresRepository extends JpaRepository<Proveedor, BigDecimal> {
}
