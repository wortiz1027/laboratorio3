package co.edu.javeriana.apiconvenios.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Convenio implements java.io.Serializable {

    private Integer codigo;
    private String nombre;
    private String descripcion;
    private Integer proveedor;

}
