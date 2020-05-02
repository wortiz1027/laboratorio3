package co.edu.javeriana.apiproveedores.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Estructura del Proveedor para registrar en la base de datos")
public class Proveedor implements java.io.Serializable {

    @ApiModelProperty(notes = "Identificador del proveedor registrado")
    private BigDecimal idProveedor;

    @ApiModelProperty(notes = "Nombre del Proveedor registrado")
    private String nombre;

    @ApiModelProperty(notes = "Descripcion del Proveedor con la informacion que lo detalla")
    private String descripcion;

    @ApiModelProperty(notes = "Fecha de creación del proveedor en formato {yyyy-MM-dd HH:mm:ss}")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fecha;

    @ApiModelProperty(notes = "Indicador de si el proveedor esta activo")
    private Boolean esActivo;

    @ApiModelProperty(notes = "Indicador de si el proveedor tiene compensacion")
    private Boolean esCompensacion;

}
