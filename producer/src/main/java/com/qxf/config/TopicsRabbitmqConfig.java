package com.qxf.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName TopicsRabbitmqConfig
 * @Description TODO
 * @Author qiuxinfa
 * @Date 2020/6/25 9:29
 **/
@Configuration
public class TopicsRabbitmqConfig {
    public static final String EMAIL_QUEUE = "email_topics";
    public static final String SMS_QUEUE = "sms_topics";
    public static final String EXCHANGE_NAME = "exchange_topics";
    public static final String EMAIL_ROUTINGKEY = "inform.#.email.#";
    // * 必须匹配一个词，不能是0个
//    public static final String EMAIL_ROUTINGKEY = "inform.*.email";
    public static final String SMS_ROUTINGKEY = "inform.#.sms.#";

    @Bean
    public TopicExchange getTopicsExchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue getTopicsEmailQueue(){
        return new Queue(EMAIL_QUEUE);
    }

    @Bean
    public Queue getTopicsSmsQueue(){
        return new Queue(SMS_QUEUE);
    }

    // 绑定交换机和队列
    @Bean
    public Binding topicsEmailExchange(){
        return BindingBuilder.bind(getTopicsEmailQueue()).to(getTopicsExchange()).with(EMAIL_ROUTINGKEY);
    }

    @Bean
    public Binding topicsSmsExchange(){
        return BindingBuilder.bind(getTopicsSmsQueue()).to(getTopicsExchange()).with(SMS_ROUTINGKEY);
    }

}
