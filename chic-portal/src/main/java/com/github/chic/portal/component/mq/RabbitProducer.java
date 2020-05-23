package com.github.chic.portal.component.mq;

import com.github.chic.common.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Rabbit 生产者
 */
@Component
public class RabbitProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendDelayMsg(Object msg, Integer delay) {
        rabbitTemplate.convertAndSend(RabbitConfig.DELAYED_EXCHANGE, RabbitConfig.DELAYED_ROUTING_KEY, msg, message -> {
            message.getMessageProperties().setDelay(delay);
            return message;
        });
    }
}
