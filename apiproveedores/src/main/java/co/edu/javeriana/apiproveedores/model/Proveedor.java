package co.edu.javeriana.apiproveedores.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;

@Entity
public class Proveedor implements java.io.Serializable {

    @Id
    private BigInteger idProveedor;
}
