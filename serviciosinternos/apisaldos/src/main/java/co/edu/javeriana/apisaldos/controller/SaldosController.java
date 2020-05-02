package co.edu.javeriana.apisaldos.controller;

import co.edu.javeriana.apisaldos.model.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/api/v1.0")
@Api(value="Gestion de consultas de sistema legado as400 via socket")
public class SaldosController {

    @Value("${as400.hostname}")
    private String hostname;

    @Value("${as400.port}")
    private int port;

    @ApiOperation(value = "Consulta el saldo disponible del usuario por el username registrado en el sistema del banco", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consulta exitosa retorna el saldo del usuario"),
            @ApiResponse(code = 400, message = "Error en el request del servicio"),
            @ApiResponse(code = 407, message = "Error en la gestion de los streams hacia o desde el servidor"),
            @ApiResponse(code = 424, message = "Error en las dependencias"),
            @ApiResponse(code = 417, message = "Error no se puede conectar al servidor as400 via socket"),
            @ApiResponse(code = 500, message = "Error en el servidor")
    })
    @GetMapping("/saldos/{username}")
    public ResponseEntity<Response> consultarSaldo(@PathVariable("username") String username) {
        try {

            if (username.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Socket socket = null;
            ObjectOutputStream oos = null;
            ObjectInputStream ois = null;

            socket = new Socket(hostname, port);

            if (!socket.isConnected()) {
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }

            oos = new ObjectOutputStream(socket.getOutputStream());

            oos.writeObject(username);

            ois = new ObjectInputStream(socket.getInputStream());

            double salario = (double) ois.readObject();

            //System.out.println(NumberFormat.getCurrencyInstance(new Locale("es", "CO")).format(salario));

            Response response = new Response(salario);

            ois.close();
            oos.close();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UnknownHostException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException ex) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        } catch (ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }

    }

}
