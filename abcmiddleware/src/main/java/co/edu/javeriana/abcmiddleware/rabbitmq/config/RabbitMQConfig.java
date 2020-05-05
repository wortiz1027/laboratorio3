package co.edu.javeriana.abcmiddleware.rabbitmq.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private static final Logger LOG = LoggerFactory.getLogger(RabbitMQConfig.class);

    @Value("${abcbank.rabbitmq.exchange}")
    String exchange;

    @Value("${abcbank.rabbitmq.queue.uservalidate}")
    String queueuservalidate;
    @Value("${abcbank.rabbitmq.routingkey.uservalidate}")
    String routingkeyuservalidate;

    @Value("${abcbank.rabbitmq.queue.checkamounttopay}")
    String queuecheckamounttopay;
    @Value("${abcbank.rabbitmq.routingkey.checkamounttopay}")
    String routingkeycheckamounttopay;

    @Value("${abcbank.rabbitmq.queue.checkbalance}")
    String queuecheckbalance;
    @Value("${abcbank.rabbitmq.routingkey.checkbalance}")
    String routingkeycheckbalance;

    @Value("${abcbank.rabbitmq.queue.paybill}")
    String queuepaybill;
    @Value("${abcbank.rabbitmq.routingkey.paybill}")
    String routingkeypaybill;

    @Value("${abcbank.rabbitmq.queue.paymentcompensation}")
    String queuepaymentcompensation;
    @Value("${abcbank.rabbitmq.routingkey.paymentcompensation}")
    String routingkeypaymentcompensation;

    @Value("${abcbank.rabbitmq.queue.notification}")
    String queuenotification;
    @Value("${abcbank.rabbitmq.routingkey.notification}")
    String routingkeynotification;

    @Bean
    public void createExchangeAndQueue(){
        try{
            Connection conn = RabbitMQConnection.getConnection();

            if(conn != null){
                Channel channel = conn.createChannel();
                channel.exchangeDeclare(exchange, "direct", true);

                // Queueuservalidate Queue
                channel.queueDeclare(queueuservalidate, true, false, false, null);
                channel.queueBind(queueuservalidate, exchange, routingkeyuservalidate);

                // Queuecheckamounttopay Queue
                channel.queueDeclare(queuecheckamounttopay, true, false, false, null);
                channel.queueBind(queuecheckamounttopay, exchange, routingkeycheckamounttopay);

                // Queuecheckbalance Queue
                channel.queueDeclare(queuecheckbalance, true, false, false, null);
                channel.queueBind(queuecheckbalance, exchange, routingkeycheckbalance);

                // Queuepaybill Queue
                channel.queueDeclare(queuepaybill, true, false, false, null);
                channel.queueBind(queuepaybill, exchange, routingkeypaybill);

                // Queuepaymentcompensation Queue
                channel.queueDeclare(queuepaymentcompensation, true, false, false, null);
                channel.queueBind(queuepaymentcompensation, exchange, routingkeypaymentcompensation);

                // Queuenotification Queue
                channel.queueDeclare(queuenotification, true, false, false, null);
                channel.queueBind(queuenotification, exchange, routingkeynotification);

                channel.close();
                conn.close();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}