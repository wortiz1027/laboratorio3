package co.edu.javeriana.abcmiddleware.rabbitmq.service;

import co.edu.javeriana.abcmiddleware.model.Factura;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
    private static final Logger LOG = LoggerFactory.getLogger(RabbitMQSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${abcbank.rabbitmq.exchange}")
    private String exchange;

    @Value("${abcbank.rabbitmq.routingkey}")
    private String routingkey;

    public void send(Factura factura) {
        LOG.info("Enviar mensaje {}", factura);
        rabbitTemplate.convertAndSend(exchange, routingkey, factura);
    }

    /*
    * TODO Realizar operaciones para cada unos de los publicadores
    */
}
