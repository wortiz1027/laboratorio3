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
@ApiModel(description = "Canónico Notificación, permite mapear información concerniente a una notificación")
public class Notificacion implements Serializable {

    @ApiModelProperty(notes = "Representa el correo electrónico de quien esta realizando el envío de la notificación.")
    private String remitente;
    @ApiModelProperty(notes = "Representa el correo electrónico de quien va a recibir la notificación.")
    private String destinatario;
    @ApiModelProperty(notes = "Representa el asunto por el cual fue enviada la notificación.")
    private String asunto;
    @ApiModelProperty(notes = "Representa el cuerpo de la notificación.")
    private String mensaje;
}
