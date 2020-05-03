package co.edu.javeriana.apiproxysap.controllers;

import co.edu.javeriana.apiproxysap.dtos.Proveedor;
import co.edu.javeriana.apiproxysap.repositories.AuditoriaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1.0")
@Api(value = "Proxy para el consumo de servicios")
public class ProxyController {

    private final AuditoriaRepository repository;

    private final RestTemplate restTemplate;

    @ApiOperation(value = "Consulta del listado de Proveedores actuales del banco", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consulta existosa de proveedores"),
            @ApiResponse(code = 400, message = "No se encontraron proveedores registrados")
    })
    @GetMapping("/proveedores")
    public ResponseEntity<List<Proveedor>> obtenerProveedores() {
        String url = "http://proveedores-service:8060/apiproveedores/api/v1.0/proveedores";
        ResponseEntity<List<Proveedor>> proveedores = restTemplate.exchange(url, HttpMethod.GET,null, new ParameterizedTypeReference<List<Proveedor>>() {});

        System.out.println("response: {}" + proveedores.getBody());
        return proveedores;
    }

}
