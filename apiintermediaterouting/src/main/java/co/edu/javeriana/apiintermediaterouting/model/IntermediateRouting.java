package co.edu.javeriana.apiintermediaterouting.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "routing", schema = "routingdb")
public class IntermediateRouting implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal idServicio;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @Column(name = "END_POINT")
    private String endPoint;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE_SERVICIO")
    private String nombreServicio;

}