package co.edu.javeriana.abcmiddleware.rabbitmq.listener;

import co.edu.javeriana.abcmiddleware.model.Factura;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;


@Service
public class ABCMessageListener implements MessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(ABCMessageListener.class);

    @Override
    public void onMessage(Message message) {
        LOG.info("Received {}", message);
        LOG.info("Realizar las operaciones necesarias....");
        LOG.info("Message processed...");
    }
}
