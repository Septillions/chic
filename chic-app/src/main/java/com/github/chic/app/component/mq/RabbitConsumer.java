package com.github.chic.app.component.mq;

import com.github.chic.common.config.RabbitConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * Rabbit 消费者
 */
@Component
public class RabbitConsumer {

    @RabbitHandler
    @RabbitListener(queues = RabbitConfig.DELAYED_QUEUE)
    public void receiveDelayMsg(String msg, Message message, Channel channel) throws IOException {
        System.out.println("发送延时消息：" + msg);
        System.out.println("收到延时消息：" + new Date());
        //int a = 1 / 0;
    }
}
