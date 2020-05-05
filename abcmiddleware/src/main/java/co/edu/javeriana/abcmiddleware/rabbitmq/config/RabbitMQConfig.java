package co.edu.javeriana.abcmiddleware.rabbitmq.config;

import co.edu.javeriana.abcmiddleware.rabbitmq.listener.ABCMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private static final Logger LOG = LoggerFactory.getLogger(RabbitMQConfig.class);

    @Value("${abcbank.rabbitmq.queue}")
    String queueName;

    @Value("${abcbank.rabbitmq.exchange}")
    String exchange;

    @Value("${abcbank.rabbitmq.routingkey}")
    private String routingkey;

    @Bean
    Queue queue() {
        LOG.info("Metodo: queue()");
        return new Queue(queueName, false);
    }

    @Bean
    DirectExchange exchange() {
        LOG.info("Metodo: exchange()");
        return new DirectExchange(exchange);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        LOG.info("Metodo: binding(Queue queue, DirectExchange exchange)");
        return BindingBuilder.bind(queue).to(exchange).with(routingkey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        LOG.info("Metodo: jsonMessageConverter()");
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        LOG.info("Metodo: rabbitTemplate(ConnectionFactory connectionFactory)");
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    /*
    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        LOG.info("Metodo: messageListenerContainer(ConnectionFactory connectionFactory)");
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueues(queue());
        simpleMessageListenerContainer.setMessageListener(new ABCMessageListener());
        return simpleMessageListenerContainer;

    }*/
}