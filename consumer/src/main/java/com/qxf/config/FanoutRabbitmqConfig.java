package com.qxf.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName FanoutRabbitmqConfig
 * @Description TODO
 * @Author qiuxinfa
 * @Date 2020/6/25 9:29
 **/
@Configuration
public class FanoutRabbitmqConfig {
    public static final String EMAIL_QUEUE = "email_fanout";
    public static final String SMS_QUEUE = "sms_fanout";
    public static final String EXCHANGE_NAME = "exchange_fanout";
    // 发布订阅模式，不用routingKey

    @Bean
    public FanoutExchange getFanoutExchange(){
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue getFanoutEmailQueue(){
        return new Queue(EMAIL_QUEUE);
    }

    @Bean
    public Queue getFanoutSmsQueue(){
        return new Queue(SMS_QUEUE);
    }

    // 绑定交换机和队列
    @Bean
    public Binding fanoutEmailExchange(){
        return BindingBuilder.bind(getFanoutEmailQueue()).to(getFanoutExchange());
    }

    @Bean
    public Binding fanoutSmsExchange(){
        return BindingBuilder.bind(getFanoutSmsQueue()).to(getFanoutExchange());
    }

}
