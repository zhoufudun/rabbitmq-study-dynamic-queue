package com.qxf.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @ClassName QueueController
 * @Description rabbitMQ 动态增减监听队列
 * @Author qiuxinfa
 * @Date 2021/9/9 23:55
 **/
@Slf4j
@RestController
public class QueueController2 {
    @Autowired
    private SimpleMessageListenerContainer messageListenerContainer;

    @PostMapping("/queue2/{queueName}")
    public String addQueue2(@PathVariable String queueName) {
        messageListenerContainer.addQueueNames(queueName);
        return "增加监听队列：" + queueName + " 成功";
    }

    @DeleteMapping("/queue2/{queueName}")
    public String deleteQueue2(@PathVariable String queueName) {
        messageListenerContainer.removeQueueNames(queueName);
        return "移除监听队列：" + queueName + " 成功";
    }

    @Bean
    public Queue myqueue() {
        return new Queue("111", true);
    }

    @Bean
    public Binding exchange(Queue myqueue,DirectExchange getDirectExchange) {
        return BindingBuilder.bind(myqueue).to(getDirectExchange).with("111");
    }


//    @Bean
//    public SimpleMessageListenerContainer simpleMessageListenerContainer(CachingConnectionFactory connectionFactory) {
//        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
//        messageListenerContainer.setQueueNames("111");
//        messageListenerContainer.setPrefetchCount(20);
//        messageListenerContainer.setShutdownTimeout(10000);
//        messageListenerContainer.setConcurrentConsumers(1);
//        messageListenerContainer.setMaxConcurrentConsumers(1);
//        messageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        messageListenerContainer.setConsumerTagStrategy(queue -> queue + "_" + UUID.randomUUID().toString());
//        messageListenerContainer.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
//            String msg = new String(message.getBody());
//            String consumerQueue = message.getMessageProperties().getConsumerQueue();
//            log.info("receive queue={}, message={}", consumerQueue, msg);
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        });
//
//        return messageListenerContainer;
//    }
}
