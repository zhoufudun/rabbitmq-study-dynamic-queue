package com.qxf.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

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
public class QueueController {
    @Autowired
    private SimpleMessageListenerContainer listenerContainer;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Resource
    private DirectExchange getDirectExchange;

    private static final String ROUTING_KEY = "dynamic_queue";

    @PostMapping("/queue/{queueName}")
    public String addQueue(@PathVariable String queueName){
        Queue queue = new Queue(queueName);
        rabbitAdmin.declareQueue(queue);
        listenerContainer.addQueues(queue);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(getDirectExchange).with(ROUTING_KEY));
        return "增加监听队列："+queueName+" 成功";
    }

    @DeleteMapping("/queue/{queueName}")
    public String deleteQueue(@PathVariable String queueName){
        listenerContainer.removeQueueNames(queueName);
        rabbitAdmin.deleteQueue(queueName);
        return "移除监听队列："+queueName+" 成功";
    }
}
