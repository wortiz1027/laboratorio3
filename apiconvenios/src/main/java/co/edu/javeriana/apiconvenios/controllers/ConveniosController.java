package co.edu.javeriana.apiconvenios.controllers;

import co.edu.javeriana.apiconvenios.dtos.Convenio;
import co.edu.javeriana.apiconvenios.repositories.ConveniosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1.0")
public class ConveniosController {

    @Autowired
    private ConveniosRepository repository;

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

    @PostMapping("/convenios")
    public ResponseEntity<Convenio> crearConvenios(@RequestBody(required = true) Convenio convenio, UriComponentsBuilder ucBuilder) {
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

}