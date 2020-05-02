package co.edu.javeriana.apiproxysap.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Estructura para realizar la auditoria de consumo")
public class Auditoria implements java.io.Serializable {

    @ApiModelProperty(notes = "Identificador del registro de Auditoria")
    private BigDecimal idAuditoria;

    @ApiModelProperty(notes = "Nombre del servicio que se consumio")
    private String servicio;

    @ApiModelProperty(notes = "Descripcion clave del consumo que se realizo")
    private String descripcion;

}
