package com.qxf.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName TestNotFoundExchange
 * @Description TODO
 * @Author qiuxinfa
 * @Date 2020/6/26 9:54
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestNotFoundExchange {
    @Autowired
    RabbitTemplate rabbitTemplate;

    // 没有找到交换机
    @Test
    public void testNotFoundExchange(){
        rabbitTemplate.convertAndSend("not exists such exchange","test","test send");
    }

    // 找到了交换机，但是没有找到绑定的队列
    @Test
    public void testNotBindingQueue(){
        rabbitTemplate.convertAndSend("testNotBindingQueue","","test send");
    }
}
