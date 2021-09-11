package com.qxf.controller;

import com.qxf.config.DeadLetterConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DeadLetterController
 * @Description TODO
 * @Author qiuxinfa
 * @Date 2021/8/30 0:06
 **/
@RestController
@RequestMapping("/dead/letter")
public class DeadLetterController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 情况一：因过期时间导致的死信
    @GetMapping("ttl/{msg}")
    public void send(@PathVariable String msg){
        MessageProperties properties = new MessageProperties();
        // 设置过期时间为5000 ms
        properties.setExpiration("5000");
        properties.setCorrelationId("123");
        Message message = new Message(msg.getBytes(),properties);
        rabbitTemplate.convertAndSend(DeadLetterConfig.EXCHANGE_NORMAL,DeadLetterConfig.KEY_NORMAL,message);
    }

    // 情况二：消息消费端调用basic.reject or basic.nack方法
    @GetMapping("/reject/{msg}")
    public void reject(@PathVariable String msg){
        rabbitTemplate.convertAndSend(DeadLetterConfig.EXCHANGE_NORMAL,DeadLetterConfig.KEY_NORMAL,msg);
    }

    // 情况三：队列已满，超出的消息会转发到死信队列
    @GetMapping("/full")
    public void full(){
        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend(DeadLetterConfig.EXCHANGE_NORMAL,
                    DeadLetterConfig.KEY_NORMAL,"测试超过队列最大长度："+i);
        }
    }

    // 情况一：因过期时间导致的死信
    @GetMapping("ttl")
    public void send(String msg,Long timeout){
        MessageProperties properties = new MessageProperties();
        // 设置过期时间
        properties.setExpiration(String.valueOf(timeout));
        properties.setCorrelationId("123");
        Message message = new Message(msg.getBytes(),properties);
        rabbitTemplate.convertAndSend(DeadLetterConfig.EXCHANGE_NORMAL,DeadLetterConfig.KEY_NORMAL,message);
    }
}
