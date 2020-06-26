package com.qxf.producer;

import com.qxf.config.TopicsRabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName ProducerTopics
 * @Description TODO
 * @Author qiuxinfa
 * @Date 2020/6/25 9:49
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTopics {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void testSendEmail(){
        String msg = "send msg to email";
        rabbitTemplate.convertAndSend(TopicsRabbitmqConfig.EXCHANGE_NAME,"inform.email",msg);
    }

    @Test
    public void testSendSms(){
        String msg = "send msg to sms";
        rabbitTemplate.convertAndSend(TopicsRabbitmqConfig.EXCHANGE_NAME,"inform.sms",msg);
    }

    @Test
    public void testSendAll(){
        String msg = "send msg to email and sms";
        rabbitTemplate.convertAndSend(TopicsRabbitmqConfig.EXCHANGE_NAME,"inform.email.sms",msg);
    }

}
