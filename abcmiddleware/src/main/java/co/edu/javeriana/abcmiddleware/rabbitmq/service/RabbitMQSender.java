package co.edu.javeriana.abcmiddleware.rabbitmq.service;

import co.edu.javeriana.abcmiddleware.model.Factura;
import co.edu.javeriana.abcmiddleware.rabbitmq.config.RabbitMQConnection;
import co.edu.javeriana.abcmiddleware.utils.ObjectAndByteCovertUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
    private static final Logger LOG = LoggerFactory.getLogger(RabbitMQSender.class);

    @Value("${abcbank.rabbitmq.exchange}")
    private String exchange;

    public void publish(String routingKey, Factura factura) {
        LOG.info("Method.publish routingKey {} and {}", routingKey, factura);

        try {
            Connection conn = RabbitMQConnection.getConnection();

            if (conn != null) {
                Channel channel = conn.createChannel();
                // First message sent by using routingKey
                channel.basicPublish(exchange, routingKey, new AMQP.BasicProperties.Builder().contentType(MediaType.APPLICATION_JSON_VALUE).build(), ObjectAndByteCovertUtil.ObjectToByte(factura));
                LOG.info(" Message Sent {}", factura);

                channel.close();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
