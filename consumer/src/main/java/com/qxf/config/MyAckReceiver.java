package com.qxf.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyAckReceiver
 * @Description TODO
 * @Author qiuxinfa
 * @Date 2020/6/26 10:08
 **/
@Component
public class MyAckReceiver implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            String consumerQueue = message.getMessageProperties().getConsumerQueue();
            String msg = new String(message.getBody());
            System.out.println("MyAckReceiver的 "+consumerQueue+" 队列，收到消息 "+msg);
            // 手动确认
            if (DeadLetterConfig.QUEUE_NORMAL.equals(consumerQueue)){
                // 测试拒绝后，转发到死信队列
                channel.basicReject(deliveryTag, false); //为true会重新放回队列
            }else {
                channel.basicAck(deliveryTag, true);
            }
        } catch (Exception e) {
            channel.basicReject(deliveryTag, false);
            e.printStackTrace();
        }
    }
}
