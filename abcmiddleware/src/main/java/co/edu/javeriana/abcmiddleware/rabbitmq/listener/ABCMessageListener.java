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


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${abcbank.rabbitmq.queue.uservalidate}"),
            exchange = @Exchange(value = "${abcbank.rabbitmq.exchange}"),
            key = "${abcbank.rabbitmq.routingkey.uservalidate}"
    ))
    public void userValidate(Message message) {
        LOG.info("Method.userValidate.Received {}", message);
        Factura factura = (Factura)ObjectAndByteCovertUtil.ByteToObject(message.getBody());

        //1. Consumimos el servicio de SAP para validar si existe la persona.
        //2. Enviamos la operación al siguiente nivel dentro de la orquestación
        rabbitMQSender.publish(routingkeycheckamounttopay, factura);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${abcbank.rabbitmq.queue.checkamounttopay}"),
            exchange = @Exchange(value = "${abcbank.rabbitmq.exchange}"),
            key = "${abcbank.rabbitmq.routingkey.checkamounttopay}"
    ))
    public void checkamounttopay(Message message) {
        LOG.info("Method.checkamounttopay.Received {}", message);
        LOG.info("Method.checkamounttopay.Realizar las operaciones necesarias ....");
        LOG.info("Method.checkamounttopay.Message processed ...");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${abcbank.rabbitmq.queue.checkbalance}"),
            exchange = @Exchange(value = "${abcbank.rabbitmq.exchange}"),
            key = "${abcbank.rabbitmq.routingkey.checkbalance}"
    ))
    public void checkbalance(Message message) {
        LOG.info("Method.checkbalance.Received {}", message);
        LOG.info("Method.checkbalance.Realizar las operaciones necesarias ....");
        LOG.info("Method.checkbalance.Message processed ...");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${abcbank.rabbitmq.queue.paybill}"),
            exchange = @Exchange(value = "${abcbank.rabbitmq.exchange}"),
            key = "${abcbank.rabbitmq.routingkey.paybill}"
    ))
    public void paybill(Message message) {
        LOG.info("Method.paybill.Received {}", message);
        LOG.info("Method.paybill.Realizar las operaciones necesarias ....");
        LOG.info("Method.paybill.Message processed ...");
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
        LOG.info("Method.notification.Realizar las operaciones necesarias ....");
        LOG.info("Method.notification.Message processed ...");
    }

}
