package co.edu.javeriana.apiintermediaterouting.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Estructura del Servicio para consultar en la base de datos")
public class Servicio implements java.io.Serializable {

    @ApiModelProperty(notes = "Identificador del servicio registrado")
    private BigDecimal idServicio;

    @ApiModelProperty(notes = "Url del servicio registrado")
    private String endPoint;

    @ApiModelProperty(notes = "nombre del servicio registrado")
    private String nombreServicio;

}