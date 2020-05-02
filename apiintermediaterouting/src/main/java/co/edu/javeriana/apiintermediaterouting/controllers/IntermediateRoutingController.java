package co.edu.javeriana.apiintermediaterouting.controllers;

import co.edu.javeriana.apiintermediaterouting.dtos.Servicio;
import co.edu.javeriana.apiintermediaterouting.model.IntermediateRouting;
import co.edu.javeriana.apiintermediaterouting.repositories.IntermediateRoutingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1.0")
@Api(value="Enrutador de invocacion de servicios")
public class IntermediateRoutingController {

    private final IntermediateRoutingRepository repository;
    private int ident;

    @ApiOperation(value = "Consulta del endpoint del servicio por identificador", response = Servicio.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consulta el servicio registrado con el nombre {nombreServicio}"),
            @ApiResponse(code = 404, message = "No se encontro informacion del servicio con el nombre {nombreServicio}")
    })
    @GetMapping("/intermediaterounting/{nombreServicio}")
    public ResponseEntity<Servicio> obtenerServicio(@PathVariable("nombreServicio") String nombreServicio) {

        System.out.println("Nombre Servicios ..... " + nombreServicio);

        if(nombreServicio.equals("Servicio1")){
            ident = 1;
        }else if(nombreServicio.equals("Servicio2")){
            ident = 2;
        }else{
            ident = 3;
        }

        Optional<co.edu.javeriana.apiintermediaterouting.model.IntermediateRouting> result = repository.findById(BigDecimal.valueOf(ident));

        if (!result.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Servicio servicio = new Servicio();

        servicio.setIdServicio(result.get().getIdServicio());
        servicio.setEndPoint(result.get().getEndPoint());

        return new ResponseEntity<>(servicio, HttpStatus.OK);
    }

}