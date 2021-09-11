package com.qxf.controller;

import com.qxf.config.FanoutRabbitmqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName FanoutController
 * @Description TODO
 * @Author qiuxinfa
 * @Date 2021/8/29 18:27
 **/
@RestController
@RequestMapping("fanout")
public class FanoutController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("send/{msg}")
    public void send(@PathVariable String msg){
       rabbitTemplate.convertAndSend(FanoutRabbitmqConfig.EXCHANGE_NAME,"",msg);
//       return "发生消息："+msg;
    }

    // 没有找到交换机
    @GetMapping("testNotFoundExchange")
    public void testNotFoundExchange(){
        rabbitTemplate.convertAndSend("not exists such exchange","test","test send");
    }

    // 找到了交换机，但是没有找到绑定的队列
    @GetMapping("testNotBindingQueue")
    public void testNotBindingQueue(){
        rabbitTemplate.convertAndSend("testNotBindingQueue","","test send");
    }
}
