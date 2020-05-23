package com.github.chic.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Rabbit 配置类
 */
@Configuration
public class RabbitConfig {
    public static final String DELAYED_EXCHANGE = "delay.exchange";
    public static final String DELAYED_QUEUE = "delay.queue";
    public static final String DELAYED_ROUTING_KEY = "delay.routingKey";

    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE, "x-delayed-message", true, false, args);
    }

    @Bean
    public Queue queue() {
        return new Queue(DELAYED_QUEUE, true);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue())
                .to(delayExchange())
                .with(DELAYED_ROUTING_KEY)
                .noargs();
    }
}
