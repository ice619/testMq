package com.cp.test3;

import com.cp.util.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Description: 发布订阅
 *
 * @author chenpeng
 * @date 2019/10/18 10:31
 */
public class ProducerFanout {
    private static final String EXCHANGE_NAME = "fanout_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MQConnectionUtils.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        String msg = "fanout_exchange_msg";
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
        System.out.println("生产者发送msg：" + msg);

        //这里连接和通道不能关，关闭后消费者收不到消息了
//        channel.close();
//        connection.close();

        // 注意：如果消费没有绑定交换机和队列，则消息会丢失

    }
}