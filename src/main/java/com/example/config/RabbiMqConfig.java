package com.example.config;
import com.example.constants.ExQueBindEnum;
import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbiMqConfig {

    /**主机*/
    private String host;
    /**端口*/
    private int port;
    /**用户名*/
    private String username;
    /**密码*/
    private String password;
    /**虚拟主机*/
    private String virtualHost;


    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(virtualHost);
        return cachingConnectionFactory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory vipFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                           @Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }


    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        AmqpAdmin amqpAdmin = new RabbitAdmin(connectionFactory);
        this.innitAdminBindTingExQu(amqpAdmin);
        return amqpAdmin;
    }

    private void innitAdminBindTingExQu(AmqpAdmin rabbitAdmin ){
        ExQueBindEnum[] exQueBindEnums = ExQueBindEnum.values();
        Map<String, Object> arguments= null;
        Exchange exchange = null;
        for (ExQueBindEnum exQueBindEnum : exQueBindEnums) {
            Boolean delayQueue = exQueBindEnum.getDelayQueue();
            if (delayQueue){
                arguments =new HashMap<>(1);
                arguments.put("x-delayed-type", ExchangeTypes.DIRECT);
                exchange = new CustomExchange(exQueBindEnum.getExchangeName(),"x-delayed-message",true,false,arguments);
            }else {
                //普通队列交换机配置方式
                exchange = new FanoutExchange(exQueBindEnum.getExchangeName(), true, false);
            }
            Queue queue = new Queue(exQueBindEnum.getQueueName(), true, false, false, arguments);
            Binding binding = BindingBuilder.bind(queue).to(exchange).with(exQueBindEnum.getRoutingKey()).noargs();
            rabbitAdmin.declareExchange(exchange);
            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareBinding(binding);
            System.out.println("[绑定]{}"+exQueBindEnum);
        }

    }













}
