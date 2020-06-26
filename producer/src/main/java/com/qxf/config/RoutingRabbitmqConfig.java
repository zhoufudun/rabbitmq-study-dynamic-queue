package com.qxf.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RoutingRabbitmqConfig
 * @Description TODO
 * @Author qiuxinfa
 * @Date 2020/6/25 9:29
 **/
@Configuration
public class RoutingRabbitmqConfig {
    public static final String EMAIL_QUEUE = "email_routing";
    public static final String SMS_QUEUE = "sms_routing";
    public static final String EXCHANGE_NAME = "exchange_routing";
    public static final String EMAIL_ROUTINGKEY = "inform.email.routingKey";
    public static final String SMS_ROUTINGKEY = "inform.sms.routingKey";

    @Bean
    public DirectExchange getDirectExchange(){
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue getDirectEmailQueue(){
        return new Queue(EMAIL_QUEUE);
    }

    @Bean
    public Queue getDirectSmsQueue(){
        return new Queue(SMS_QUEUE);
    }

    // 绑定交换机和队列
    @Bean
    public Binding directEmailExchange(){
        return BindingBuilder.bind(getDirectEmailQueue()).to(getDirectExchange()).with(EMAIL_ROUTINGKEY);
    }

    @Bean
    public Binding directSmsExchange(){
        return BindingBuilder.bind(getDirectSmsQueue()).to(getDirectExchange()).with(SMS_ROUTINGKEY);
    }

}
