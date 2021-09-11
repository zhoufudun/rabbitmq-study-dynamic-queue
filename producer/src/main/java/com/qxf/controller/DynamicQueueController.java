package com.qxf.controller;

import com.qxf.config.RoutingRabbitmqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName DynamicQueueController
 * @Description rabbitMQ 动态增减监听队列
 * @Author qiuxinfa
 * @Date 2021/9/10 23:14
 **/
@RestController
public class DynamicQueueController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static RestTemplate restTemplate = new RestTemplate();

    private static final String ROUTING_KEY = "dynamic_queue";

    @GetMapping("/addQueue")
    public String addQueue(String queueName){
        String url = "http://192.168.231.1:8888/queue/" + queueName;
        return restTemplate.postForObject(url,null,String.class);
    }

    @GetMapping("/push/{msg}")
    public String pushMsg(@PathVariable String msg){
        rabbitTemplate.convertAndSend(RoutingRabbitmqConfig.EXCHANGE_NAME,ROUTING_KEY,msg);
        return "发送成功";
    }

    @GetMapping("/deleteQueue")
    public String deleteQueue(String queueName){
        String url = "http://192.168.231.1:8888/queue/" + queueName;
        restTemplate.delete(url);
        return "删除队列成功";
    }
}
