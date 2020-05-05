package co.edu.javeriana.apiproxysap.controllers;

import co.edu.javeriana.apiproxysap.dtos.Auditoria;
import co.edu.javeriana.apiproxysap.dtos.Proveedor;
import co.edu.javeriana.apiproxysap.dtos.RequestRabbit;
import co.edu.javeriana.apiproxysap.repositories.AuditoriaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @ApiOperation(value = "Consumir la cola para realizar el proceso para un cliente", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consumo exitoso"),
            @ApiResponse(code = 500, message = "Error al consumir el servicio")
    })
    @PostMapping(value = "/factura")
    public ResponseEntity<String> obtenerFactura(@RequestBody RequestRabbit request) {
        try {
            String url = "http://localhost:8080/paymentService/factura";
            restTemplate.postForEntity(url, request, null);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
    }

}
