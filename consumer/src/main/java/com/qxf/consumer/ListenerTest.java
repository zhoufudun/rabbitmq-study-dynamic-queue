package com.qxf.consumer;

import com.qxf.config.FanoutRabbitmqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName ListenerTest
 * @Description TODO
 * @Author qiuxinfa
 * @Date 2021/9/7 23:35
 **/
@Component
public class ListenerTest {

    @RabbitListener(queues = {FanoutRabbitmqConfig.EMAIL_QUEUE,FanoutRabbitmqConfig.SMS_QUEUE})
    public void test(Message message){
        System.out.println("ListenerTest收到消息： "+new String(message.getBody()));
        System.out.println(message.getMessageProperties());
    }
}
