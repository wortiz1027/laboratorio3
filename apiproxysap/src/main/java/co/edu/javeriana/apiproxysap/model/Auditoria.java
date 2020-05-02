package co.edu.javeriana.apiproxysap.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "auditoria", schema = "proxysapdb")
public class Auditoria implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_AUDITORIA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal idAuditoria;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @Column(name = "SERVICIO")
    private String servicio;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @Column(name = "DESCRIPCION")
    private String descripcion;

}
