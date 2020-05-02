package co.edu.javeriana.apiintermediaterouting.controllers;

import co.edu.javeriana.apiintermediaterouting.dtos.Servicio;
import co.edu.javeriana.apiintermediaterouting.mail.EnvioEmail;
import co.edu.javeriana.apiintermediaterouting.repositories.IntermediateRoutingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.script.ScriptTemplateConfig;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1.0")
@Api(value="Enrutador de invocacion de servicios")
public class IntermediateRoutingController {

    @Value("${mensaje.destino}")
    private String destino;

    @Value("${mensaje.asunto}")
    private String asunto;

    @Value("${mensaje.contenido}")
    private String contenido;

    private final IntermediateRoutingRepository repository;
    private final EnvioEmail enviarMail;

    @ApiOperation(value = "Consulta del endpoint del servicio por nombre", response = Servicio.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consulta el servicio registrado con el nombre {nombreServicio}"),
            @ApiResponse(code = 404, message = "No se encontro informacion del servicio con el nombre {nombreServicio}")
    })
    @GetMapping("/routing/{nombreServicio}/servicio")
    public ResponseEntity<Servicio> obtenerServiciobyName(@PathVariable("nombreServicio") String nombreServicio) {

        Optional<co.edu.javeriana.apiintermediaterouting.model.IntermediateRouting> result = Optional.ofNullable(repository.getbyName(nombreServicio));

        if (!result.isPresent()) {
            enviarMail.sendEmail(this.destino, this.asunto, this.contenido);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Servicio servicio = new Servicio();

        servicio.setIdServicio(result.get().getIdServicio());
        servicio.setEndPoint(result.get().getEndPoint());
        servicio.setNombreServicio(result.get().getNombreServicio());

        return new ResponseEntity<>(servicio, HttpStatus.OK);
    }

    @ApiOperation(value = "Consulta del endpoint del servicio por id", response = Servicio.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consulta el servicio registrado con el id {id}"),
            @ApiResponse(code = 404, message = "No se encontro informacion del servicio con el id {id}")
    })
    @GetMapping("/routing/{id}")
    public ResponseEntity<Servicio> obtenerServiciobyId(@PathVariable("id") BigDecimal id) {

        Optional<co.edu.javeriana.apiintermediaterouting.model.IntermediateRouting> result = repository.findById(id);

        if (!result.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Servicio servicio = new Servicio();

        servicio.setIdServicio(result.get().getIdServicio());
        servicio.setEndPoint(result.get().getEndPoint());
        servicio.setNombreServicio(result.get().getNombreServicio());

        return new ResponseEntity<>(servicio, HttpStatus.OK);
    }

    @ApiOperation(value = "Crea un registro en base de datos con la informacion del servicio", response = Servicio.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Creacion exitosa del registo del servicio")
    })
    @PostMapping("/routing")
    public ResponseEntity<Servicio> crearServicio(@RequestBody(required = true) Servicio servicio, UriComponentsBuilder ucBuilder) {
        co.edu.javeriana.apiintermediaterouting.model.IntermediateRouting item = new co.edu.javeriana.apiintermediaterouting.model.IntermediateRouting();

        item.setEndPoint(servicio.getEndPoint());
        item.setNombreServicio(servicio.getNombreServicio());

        item = repository.saveAndFlush(item);

        servicio.setIdServicio(item.getIdServicio());

        HttpHeaders header = new HttpHeaders();
        header.setLocation(ucBuilder.path("/routing/{id}").buildAndExpand(servicio.getIdServicio()).toUri());

        return new ResponseEntity<>(servicio, header, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Elimina el servicio registrado por id", response = Servicio.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Eliminacion exitosa del servicio identificado con el id {id}"),
            @ApiResponse(code = 404, message = "No se encontro informacion del servicio para eliminar con el id {id}")
    })
    @DeleteMapping("/routing/{id}")
    public ResponseEntity<Servicio> eliminarServicio(@PathVariable("id") BigDecimal id) {
        try {
            Optional<co.edu.javeriana.apiintermediaterouting.model.IntermediateRouting> result = repository.findById(id);

            if (!result.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}