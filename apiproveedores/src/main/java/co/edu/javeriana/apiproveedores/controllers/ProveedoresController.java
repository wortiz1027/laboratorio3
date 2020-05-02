package co.edu.javeriana.apiproveedores.controllers;

import co.edu.javeriana.apiproveedores.dtos.Proveedor;
import co.edu.javeriana.apiproveedores.repositories.ProveedoresRepository;
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
@RequestMapping("/api/v1.0")
@Api(value = "Gestion de proveedores de servicios publicos para el banco ABC")
public class ProveedoresController {

    private final ProveedoresRepository repository;

    @ApiOperation(value = "Consulta del listado de Proveedores actuales del banco", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consulta existosa de proveedores"),
            @ApiResponse(code = 400, message = "No se encontraron proveedores registrados")
    })
    @GetMapping("/proveedores")
    public ResponseEntity<List<Proveedor>> obtenerProveedores() {
        List<Proveedor> proveedores = repository.findAll()
                .stream()
                .map(p -> {
                    Proveedor item = new Proveedor();

                    item.setIdProveedor(p.getIdProveedor());
                    item.setFecha(p.getFecha());
                    item.setNombre(p.getNombre());
                    item.setEsActivo(p.getEsActivo());
                    item.setDescripcion(p.getDescripcion());
                    item.setEsCompensacion(p.getEsCompensacion());

                    return item;
                }).collect(Collectors.toList());
        if (proveedores.isEmpty()) {
            return new ResponseEntity<>(proveedores, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }

    @ApiOperation(value = "Consulta del Proveedor por codigo", response = Proveedor.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consulta del proveedor por codigo {id}"),
            @ApiResponse(code = 400, message = "No se encontraron informacion de proveedor con el codigo {id}")
    })
    @GetMapping("/proveedores/{id}")
    public ResponseEntity<Proveedor> obtenerProveedor(@PathVariable("id") BigDecimal id) {
        Optional<co.edu.javeriana.apiproveedores.model.Proveedor> result = repository.findById(id);

        if (!result.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Proveedor proveedor = new Proveedor();

        proveedor.setIdProveedor(result.get().getIdProveedor());
        proveedor.setNombre(result.get().getNombre());
        proveedor.setDescripcion(result.get().getDescripcion());
        proveedor.setFecha(result.get().getFecha());
        proveedor.setEsActivo(result.get().getEsActivo());
        proveedor.setEsCompensacion(result.get().getEsCompensacion());

        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    @ApiOperation(value = "Crea un registro en base de datos con la informacion del proveedor", response = Proveedor.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Creacion exitosa del registro del proveedor")
    })
    @PostMapping("/proveedores")
    public ResponseEntity<Proveedor> crearProveedor(@RequestBody(required = true) Proveedor proveedor, UriComponentsBuilder ucBuilder) {
        co.edu.javeriana.apiproveedores.model.Proveedor item = new co.edu.javeriana.apiproveedores.model.Proveedor();

        item.setNombre(proveedor.getNombre());
        item.setDescripcion(proveedor.getDescripcion());
        item.setFecha(proveedor.getFecha());
        item.setEsActivo(proveedor.getEsActivo());
        item.setEsCompensacion(proveedor.getEsCompensacion());

        item = repository.saveAndFlush(item);

        proveedor.setIdProveedor(item.getIdProveedor());

        HttpHeaders header = new HttpHeaders();
        header.setLocation(ucBuilder.path("/convenios/{id}").buildAndExpand(proveedor.getIdProveedor()).toUri());

        return new ResponseEntity<>(proveedor, header, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualiza informacion del registro consultado por codigo", response = Proveedor.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Actualizacion exitosa del proveedor identificado con el codigo {id}"),
            @ApiResponse(code = 404, message = "No se encontro informacion del proveedor para actualizar con el codigo {id}")
    })
    @PutMapping("/proveedores/{id}")
    public ResponseEntity<Proveedor> actualizarProveedor(@PathVariable("id") BigDecimal id, @RequestBody(required = true) Proveedor proveedor) {
        try {
            Optional<co.edu.javeriana.apiproveedores.model.Proveedor> result = repository.findById(id);

            if (!result.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            result.get().setNombre(proveedor.getNombre());
            result.get().setDescripcion(proveedor.getDescripcion());
            result.get().setFecha(proveedor.getFecha());
            result.get().setEsActivo(proveedor.getEsActivo());
            result.get().setIdProveedor(proveedor.getIdProveedor());
            result.get().setEsCompensacion(proveedor.getEsCompensacion());

            repository.save(result.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Elimina el proveedor registrado por codigo", response = Proveedor.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Eliminacion exitosa del proveedor identificado con el codigo {id}"),
            @ApiResponse(code = 404, message = "No se encontro informacion del proveedor para eliminar con el codigo {id}")
    })
    @DeleteMapping("/proveedores/{id}")
    public ResponseEntity<Proveedor> eliminarProveedor(@PathVariable("id") BigDecimal id) {
        try {
            Optional<co.edu.javeriana.apiproveedores.model.Proveedor> result = repository.findById(id);

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
