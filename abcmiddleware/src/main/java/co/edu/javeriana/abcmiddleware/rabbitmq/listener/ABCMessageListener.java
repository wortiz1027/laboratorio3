package co.edu.javeriana.abcmiddleware.rabbitmq.listener;

import co.edu.javeriana.abcmiddleware.model.Factura;
import co.edu.javeriana.abcmiddleware.rabbitmq.service.RabbitMQSender;
import co.edu.javeriana.abcmiddleware.utils.ObjectAndByteCovertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ABCMessageListener{
    private static final Logger LOG = LoggerFactory.getLogger(ABCMessageListener.class);

    @Autowired
    RabbitMQSender rabbitMQSender;

    @Value("${abcbank.rabbitmq.exchange}")
    String exchange;

    @Value("${abcbank.rabbitmq.routingkey.uservalidate}")
    String routingkeyuservalidate;

    @Value("${abcbank.rabbitmq.routingkey.checkamounttopay}")
    String routingkeycheckamounttopay;

    @Value("${abcbank.rabbitmq.routingkey.checkbalance}")
    String routingkeycheckbalance;

    @Value("${abcbank.rabbitmq.routingkey.paybill}")
    String routingkeypaybill;

    @Value("${abcbank.rabbitmq.routingkey.paymentcompensation}")
    String routingkeypaymentcompensation;

    @Value("${abcbank.rabbitmq.routingkey.notification}")
    String routingkeynotification;

    //private final RestTemplate restTemplate;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${abcbank.rabbitmq.queue.uservalidate}"),
            exchange = @Exchange(value = "${abcbank.rabbitmq.exchange}"),
            key = "${abcbank.rabbitmq.routingkey.uservalidate}"
    ))
    public void userValidate(Message message) {
        LOG.info("Method.userValidate.Received {}", message);
        Factura factura = (Factura)ObjectAndByteCovertUtil.ByteToObject(message.getBody());
        LOG.info("Method.userValidate.Publishing...........");
        rabbitMQSender.publish(routingkeycheckamounttopay, factura);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${abcbank.rabbitmq.queue.checkamounttopay}"),
            exchange = @Exchange(value = "${abcbank.rabbitmq.exchange}"),
            key = "${abcbank.rabbitmq.routingkey.checkamounttopay}"
    ))
    public void checkamounttopay(Message message) {
        LOG.info("Method.checkamounttopay.Received {}", message);
        Factura factura = (Factura)ObjectAndByteCovertUtil.ByteToObject(message.getBody());
        LOG.info("Method.checkamounttopay.Publishing...........");
        rabbitMQSender.publish(routingkeycheckbalance, factura);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${abcbank.rabbitmq.queue.checkbalance}"),
            exchange = @Exchange(value = "${abcbank.rabbitmq.exchange}"),
            key = "${abcbank.rabbitmq.routingkey.checkbalance}"
    ))
    public void checkbalance(Message message) {
        LOG.info("Method.checkbalance.Received {}", message);
        Factura factura = (Factura)ObjectAndByteCovertUtil.ByteToObject(message.getBody());
        LOG.info("Method.checkbalance.Publishing...........");
        rabbitMQSender.publish(routingkeypaybill, factura);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${abcbank.rabbitmq.queue.paybill}"),
            exchange = @Exchange(value = "${abcbank.rabbitmq.exchange}"),
            key = "${abcbank.rabbitmq.routingkey.paybill}"
    ))
    public void paybill(Message message) {
        LOG.info("Method.paybill.Received {}", message);
        Factura factura = (Factura)ObjectAndByteCovertUtil.ByteToObject(message.getBody());
        LOG.info("Method.paybill.Publishing...........");
        rabbitMQSender.publish(routingkeynotification, factura);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${abcbank.rabbitmq.queue.paymentcompensation}"),
            exchange = @Exchange(value = "${abcbank.rabbitmq.exchange}"),
            key = "${abcbank.rabbitmq.routingkey.paymentcompensation}"
    ))
    public void paymentcompensation(Message message) {
        LOG.info("Method.paymentcompensation.Received {}", message);
        LOG.info("Method.paymentcompensation.Realizar las operaciones necesarias ....");
        LOG.info("Method.paymentcompensation.Message processed ...");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${abcbank.rabbitmq.queue.notification}"),
            exchange = @Exchange(value = "${abcbank.rabbitmq.exchange}"),
            key = "${abcbank.rabbitmq.routingkey.notification}"
    ))
    public void notification(Message message) {
        LOG.info("Method.notification.Received {}", message);
        Factura factura = (Factura)ObjectAndByteCovertUtil.ByteToObject(message.getBody());
        LOG.info("Method.paybill.Notifications success...........");
    }

}
