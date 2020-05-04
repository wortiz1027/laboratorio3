package co.edu.javeriana.abcmiddleware.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Canónico del Proveedor que permite mapear la información de un proveedor dentro de la orquestación de servicios")
public class Proveedor implements Serializable {
    private static final long serialVersionUID = -3285455307232818662L;

    @ApiModelProperty(notes = "Representa el tipo de proveedor a manejar, GAS, LUZ, AGUA, etc.")
    private String tipoProveedor;
    @ApiModelProperty(notes = "Representa el echo de que el proveedor cuente con una cuenta de soporte de compesanción")
    private Boolean cuentaConSoporteCompesancion = Boolean.TRUE;
}
