package com.qxf.consumer;

import com.qxf.config.FanoutRabbitmqConfig;
import com.qxf.config.RoutingRabbitmqConfig;
import com.qxf.config.TopicsRabbitmqConfig;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;


/**
 * @ClassName ReceiveHandler
 * @Description TODO
 * @Author qiuxinfa
 * @Date 2020/6/25 10:05
 **/
@Component
public class TopicsConsumer {
    // topics模式
    @RabbitHandler
    @RabbitListener(queues = TopicsRabbitmqConfig.EMAIL_QUEUE)
    public void receiveEmail(String msg){
        System.out.println("topics收到email消息： "+msg);
    }

    @RabbitHandler
    @RabbitListener(queues = TopicsRabbitmqConfig.SMS_QUEUE)
    public void receiveSms(String msg){
        System.out.println("topics收到sms消息： "+msg);
    }


    // routing模式
    @RabbitHandler
    @RabbitListener(queues = RoutingRabbitmqConfig.EMAIL_QUEUE)
    public void receiveRoutingEmail(String msg){
        System.out.println("routing收到email消息： "+msg);
    }

    @RabbitHandler
    @RabbitListener(queues = RoutingRabbitmqConfig.SMS_QUEUE)
    public void receiveRoutingSms(String msg){
        System.out.println("routing收到sms消息： "+msg);
    }

    // 发布订阅模式
    @RabbitHandler
    @RabbitListener(queues = FanoutRabbitmqConfig.EMAIL_QUEUE)
    public void receiveFanoutEmail(String msg){
        System.out.println("fanout收到email消息： "+msg);
    }

    @RabbitHandler
    @RabbitListener(queues = FanoutRabbitmqConfig.SMS_QUEUE)
    public void receiveFanoutSms(String msg){
        System.out.println("fanout收到sms消息： "+msg);
    }

}
