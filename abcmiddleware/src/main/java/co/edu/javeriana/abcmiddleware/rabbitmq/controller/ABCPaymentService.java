package co.edu.javeriana.abcmiddleware.rabbitmq.controller;

import co.edu.javeriana.abcmiddleware.rabbitmq.service.RabbitMQSender;
import co.edu.javeriana.abcmiddleware.model.Factura;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paymentService")
public class ABCPaymentService {
    private static final Logger LOG = LoggerFactory.getLogger(ABCPaymentService.class);

    @Autowired
    RabbitMQSender rabbitMQSender;

    /*
    Ejemplo de entrada JSON
    {
        "codigo": "F1002",
        "cliente": {
            "usuario": "efranco2"
        },
        "proveedor": {
            "tipoProveedor": "AGUA"
        },
        "valor": 11000
    }
    * */
    @PostMapping(path = "/factura",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void paymentOneClic(@RequestBody Factura factura) {
        LOG.info("REST paymentOneClic.factura {}", factura);
        rabbitMQSender.send(factura);
    }
}