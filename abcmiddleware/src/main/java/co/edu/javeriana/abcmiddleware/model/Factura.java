package co.edu.javeriana.abcmiddleware.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Canónico de la Factura que permite mapear la información de una factura dentro de la orquestación de servicios")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codigo")
public class Factura implements Serializable {
    private static final long serialVersionUID = 9036833078455819824L;

    @ApiModelProperty(notes = "Código único de la factura")
    private String codigo;
    @ApiModelProperty(notes = "Representa la información de un cliente")
    private Cliente cliente = new Cliente();
    @ApiModelProperty(notes = "Representa la información de un proveedor")
    private Proveedor proveedor = new Proveedor();
    @ApiModelProperty(notes = "Representa el valor total a pagar por un cliente a un proveedor determinado")
    private BigDecimal valor = new BigDecimal(0);
}
