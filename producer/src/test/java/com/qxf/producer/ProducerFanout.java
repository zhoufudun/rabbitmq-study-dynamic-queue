package com.qxf.producer;

import com.qxf.config.FanoutRabbitmqConfig;
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
public class ProducerFanout {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void testSend(){
        String msg = "send msg to email and sms";
        rabbitTemplate.convertAndSend(FanoutRabbitmqConfig.EXCHANGE_NAME,"",msg);
    }

}
