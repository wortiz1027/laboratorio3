package co.edu.javeriana.apiproveedores.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "proveedor", schema = "proveedoresdb")
public class Proveedor implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_PROVEEDOR")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal idProveedor;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE")
    private String nombre;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @NotNull
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @NotNull
    @Column(name = "ACTIVO", columnDefinition = "TINYINT(1)")
    private Boolean esActivo;

    @NotNull
    @Column(name = "COMPENSACION", columnDefinition = "TINYINT(1)")
    private Boolean esCompensacion;

}
