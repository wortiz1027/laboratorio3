package co.edu.javeriana.apiconvenios.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Convenio implements java.io.Serializable {

    private BigDecimal idConvenio;
    private String nombre;
    private String descripcion;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date fecha;
    private Boolean esActivo;
    private BigDecimal proveedor;

}
