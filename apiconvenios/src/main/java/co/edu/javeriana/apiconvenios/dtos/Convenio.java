package co.edu.javeriana.apiconvenios.dtos;

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
@ApiModel(description = "Estructura del Convenio para registrar en la base de datos")
public class Convenio implements java.io.Serializable {

    @ApiModelProperty(notes = "Identificador del convenio registrado")
    private BigDecimal idConvenio;

    @ApiModelProperty(notes = "Nombre del convenio registrado")
    private String nombre;

    @ApiModelProperty(notes = "Descripcion del convenio con la informacion que lo detalla")
    private String descripcion;

    @ApiModelProperty(notes = "Fecha de creacion del convenio en formato {yyyy-MM-dd HH:mm:ss}")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date fecha;

    @ApiModelProperty(notes = "Indicador de si el convenio esta activo")
    private Boolean esActivo;

    @ApiModelProperty(notes = "Codigo del proveedor asociado al convenio registrado")
    private BigDecimal proveedor;

}