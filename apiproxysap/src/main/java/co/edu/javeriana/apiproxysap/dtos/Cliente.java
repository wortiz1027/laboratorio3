package co.edu.javeriana.apiproxysap.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Canónico del Cliente que permite mapear la información de un cliente dentro de la orquestación de servicios")
public class Cliente implements Serializable {
    private static final long serialVersionUID = -1779946922428098743L;

    @ApiModelProperty(notes = "Representa el usuario de un cliente")
    private String usuario;
}
