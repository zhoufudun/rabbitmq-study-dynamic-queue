package com.qxf.producer;

import com.qxf.config.RoutingRabbitmqConfig;
import com.qxf.config.TopicsRabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName ProducerRouting
 * @Description TODO
 * @Author qiuxinfa
 * @Date 2020/6/26 9:23
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerRouting {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void testSendEmail(){
        String msg = "send msg to email";
        rabbitTemplate.convertAndSend(RoutingRabbitmqConfig.EXCHANGE_NAME,"inform.email.routingKey",msg);
    }

    @Test
    public void testSendSms(){
        String msg = "send msg to sms";
        rabbitTemplate.convertAndSend(RoutingRabbitmqConfig.EXCHANGE_NAME,"inform.sms.routingKey",msg);
    }
}
