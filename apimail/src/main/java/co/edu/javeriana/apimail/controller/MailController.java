package co.edu.javeriana.apimail.controller;

import co.edu.javeriana.apimail.email.EMail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1.0")
@Api(value="Api rest para enviar correo electronico a soporte del banco")
public class MailController {

    @Value("${mensaje.destino}")
    private String destino;

    @Value("${mensaje.asunto}")
    private String asunto;

    @Value("${mensaje.contenido}")
    private String contenido;

    private final EMail mail;

    @ApiOperation(value = "Envio de correo electronico", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Correo electronico enviado exitosamente"),
            @ApiResponse(code = 400, message = "Error en el request de la operacion"),
            @ApiResponse(code = 500, message = "Error en el servidor enviando el correo electronico")
    })
    @GetMapping("/email/{servicio}")
    public ResponseEntity<Void> enviar(@PathVariable("servicio") String servicio) {
        try {

            if (servicio.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            mail.sendEmail(this.destino, this.asunto, String.format(this.contenido, servicio));
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
