package co.edu.javeriana.abcmiddleware.rabbitmq.controller;

import co.edu.javeriana.abcmiddleware.model.Factura;
import co.edu.javeriana.abcmiddleware.rabbitmq.service.RabbitMQSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paymentService")
public class ABCPaymentService {
    private static final Logger LOG = LoggerFactory.getLogger(ABCPaymentService.class);

    @Autowired
    RabbitMQSender rabbitMQSender;

    @Value("${abcbank.rabbitmq.routingkey.uservalidate}")
    String routingkeyuservalidate;

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
        rabbitMQSender.publish(routingkeyuservalidate, factura);
    }
}