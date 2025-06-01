package com.qxf.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @ClassName MessageListenerConfig
 * @Description TODO
 * @Author qiuxinfa
 * @Date 2020/6/26 10:08
 **/
@Slf4j
@Configuration
public class MessageListenerConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private MyAckReceiver myAckReceiver;//消息接收处理类

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // RabbitMQ默认是自动确认，这里改为手动确认消息
        //设置一个队列
        container.setQueueNames(FanoutRabbitmqConfig.EMAIL_QUEUE,FanoutRabbitmqConfig.SMS_QUEUE,
                RoutingRabbitmqConfig.EMAIL_QUEUE,RoutingRabbitmqConfig.SMS_QUEUE,
                TopicsRabbitmqConfig.EMAIL_QUEUE,TopicsRabbitmqConfig.SMS_QUEUE
                ,DeadLetterConfig.QUEUE_DEAD,DeadLetterConfig.QUEUE_NORMAL);
        container.setMessageListener(myAckReceiver);

        return container;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(){
        return new RabbitAdmin(connectionFactory);
    }
}