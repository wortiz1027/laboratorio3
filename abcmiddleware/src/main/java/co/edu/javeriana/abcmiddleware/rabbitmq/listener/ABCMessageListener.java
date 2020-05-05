package co.edu.javeriana.abcmiddleware.rabbitmq.listener;

import co.edu.javeriana.abcmiddleware.model.Factura;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class ABCMessageListener{
    private static final Logger LOG = LoggerFactory.getLogger(ABCMessageListener.class);

    @RabbitListener(queues = "${abcbank.rabbitmq.queue}")
    public void customerFactura(Factura factura) {
        LOG.info("Received {}", factura);
        LOG.info("Realizar las operaciones necesarias....");
        LOG.info("Message processed...");
    }

    /*
    * TODO Crear operaciones para cada uno de los subcriptores
    */
}
