package co.edu.javeriana.apiconvenios.controllers;

import co.edu.javeriana.apiconvenios.dtos.Convenio;
import co.edu.javeriana.apiconvenios.repositories.ConveniosRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1.0")
@Api(value="Gestion de convenios entre el banco ABC y los proveedores de servicios publicos")
public class ConveniosController {

    private final ConveniosRepository repository;

    @ApiOperation(value = "Consulta del listado de convenios registrados en el banco", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consulta exitosa del listado de convenios registrados"),
            @ApiResponse(code = 204, message = "No se encontraron convenios registados en la base de datos")
    })
    @GetMapping("/convenios")
    public ResponseEntity<List<Convenio>> obtenerConvenios() {

        List<Convenio> convenios = repository.findAll()
                                             .stream()
                                             .map(c -> {
                                                        Convenio item = new Convenio();

                                                        item.setIdConvenio(c.getIdConvenio());
                                                        item.setNombre(c.getNombre());
                                                        item.setDescripcion(c.getDescripcion());
                                                        item.setFecha(c.getFecha());
                                                        item.setEsActivo(c.getEsActivo());
                                                        item.setProveedor(c.getProveedor());

                                                        return item;
                                                    }).collect(Collectors.toList());

        if (convenios.isEmpty()) {
            return new ResponseEntity<>(convenios, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(convenios, HttpStatus.OK);
    }

    @ApiOperation(value = "Consulta del convenio registrado por codigo", response = Convenio.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consulta el convenio registrado por codigo {id}"),
            @ApiResponse(code = 404, message = "No se encontro informacion del convenio con el codigo {id}")
    })
    @GetMapping("/convenios/{id}")
    public ResponseEntity<Convenio> obtenerConvenio(@PathVariable("id") BigDecimal id) {

        Optional<co.edu.javeriana.apiconvenios.model.Convenio> result = repository.findById(id);

        if (!result.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Convenio convenio = new Convenio();

        convenio.setIdConvenio(result.get().getIdConvenio());
        convenio.setNombre(result.get().getNombre());
        convenio.setDescripcion(result.get().getDescripcion());
        convenio.setFecha(result.get().getFecha());
        convenio.setEsActivo(result.get().getEsActivo());
        convenio.setProveedor(result.get().getProveedor());

        return new ResponseEntity<>(convenio, HttpStatus.OK);
    }

    @ApiOperation(value = "Crea un registro en base de datos con la informacion del convenio", response = Convenio.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Creacion exitos del reggisto del convenio")
    })
    @PostMapping("/convenios")
    public ResponseEntity<Convenio> crearConvenio(@RequestBody(required = true) Convenio convenio, UriComponentsBuilder ucBuilder) {
        co.edu.javeriana.apiconvenios.model.Convenio item = new co.edu.javeriana.apiconvenios.model.Convenio();

        item.setNombre(convenio.getNombre());
        item.setDescripcion(convenio.getDescripcion());
        item.setFecha(convenio.getFecha());
        item.setEsActivo(convenio.getEsActivo());
        item.setProveedor(convenio.getProveedor());

        item = repository.saveAndFlush(item);

        convenio.setIdConvenio(item.getIdConvenio());

        HttpHeaders header = new HttpHeaders();
        header.setLocation(ucBuilder.path("/convenios/{id}").buildAndExpand(convenio.getIdConvenio()).toUri());

        return new ResponseEntity<>(convenio, header, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualiza informacion del registro consultado por codigo", response = Convenio.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Actualizacion exitosa del convenio identificado con el codigo {id}"),
            @ApiResponse(code = 404, message = "No se encontro informacion del convenio para actualizar con el codigo {id}")
    })
    @PutMapping("/convenios/{id}")
    public ResponseEntity<Convenio> actualizarConvenio(@PathVariable("id") BigDecimal id, @RequestBody(required = true) Convenio convenio) {
        try {
            Optional<co.edu.javeriana.apiconvenios.model.Convenio> result = repository.findById(id);

            if (!result.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            result.get().setNombre(convenio.getNombre());
            result.get().setDescripcion(convenio.getDescripcion());
            result.get().setFecha(convenio.getFecha());
            result.get().setEsActivo(convenio.getEsActivo());
            result.get().setProveedor(convenio.getProveedor());

            repository.save(result.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Elimina el convenio registrado por codigo", response = Convenio.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Eliminacion exitosa del convenio identificado con el codigo {id}"),
            @ApiResponse(code = 404, message = "No se encontro informacion del convenio para eliminar con el codigo {id}")
    })
    @DeleteMapping("/convenios/{id}")
    public ResponseEntity<Convenio> eliminarConvenio(@PathVariable("id") BigDecimal id) {
        try {
            Optional<co.edu.javeriana.apiconvenios.model.Convenio> result = repository.findById(id);

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