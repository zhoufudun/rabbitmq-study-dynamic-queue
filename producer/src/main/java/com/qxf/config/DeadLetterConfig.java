package com.qxf.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DeadLetterConfig
 * @Description 死信配置
 * @Author qiuxinfa
 * @Date 2020/6/25 9:29
 **/
@Configuration
public class DeadLetterConfig {
    // 正常的交换机
    public static final String EXCHANGE_NORMAL = "exchange_normal";
    // 死信交换机
    public static final String EXCHANGE_DEAD = "exchange_dead";
    // 正常的队列
    public static final String QUEUE_NORMAL = "queue_normal";
    // 死信队列
    public static final String QUEUE_DEAD = "queue_dead";
    public static final String KEY_NORMAL = "key_normal";
    public static final String KEY_DEAD = "key_dead";


    @Bean
    public DirectExchange getNormalExchange(){
        return new DirectExchange(EXCHANGE_NORMAL);
    }

    @Bean
    public Queue getNormalQueue(){
        Map<String,Object> map = new HashMap<>(4);
        // 正常队列设置死信交换机 参数 key 是固定值
        map.put("x-dead-letter-exchange",EXCHANGE_DEAD);
        // 正常队列设置死信 routing-key  参数 key 是固定值
        map.put("x-dead-letter-routing-key",KEY_DEAD);
        // 设置过期时间5000 ms
//        map.put("x-message-ttl",5000);
        // 设置队列的最大长度
        map.put("x-max-length",3);
        return QueueBuilder.durable(QUEUE_NORMAL).withArguments(map).build();
    }

    // 绑定交换机和队列
    @Bean
    public Binding bindingNormal(){
        return BindingBuilder.bind(getNormalQueue()).to(getNormalExchange()).with(KEY_NORMAL);
    }

    @Bean
    public DirectExchange getDeadExchange(){
        return new DirectExchange(EXCHANGE_DEAD);
    }

    @Bean
    public Queue getDeadQueue(){
        return QueueBuilder.durable(QUEUE_DEAD).build();
    }

    // 绑定交换机和队列
    @Bean
    public Binding bindingDead(){
        return BindingBuilder.bind(getDeadQueue()).to(getDeadExchange()).with(KEY_DEAD);
    }


}
