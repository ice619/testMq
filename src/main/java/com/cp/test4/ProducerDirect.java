package com.cp.test4;

import com.cp.util.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Description: Direct
 *
 * @author chenpeng
 * @date 2019/10/18 11:16
 */
public class ProducerDirect {
    private static final String EXCHANGE_NAME = "direct_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MQConnectionUtils.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");

        String routingKey = "info";
        String msg = "direct_exchange_msg -" + routingKey;
        channel.basicPublish(EXCHANGE_NAME,routingKey, null,msg.getBytes());
        System.out.println("生产者发送msg：" + msg);

        // // 5.关闭通道、连接
        // channel.close();
        // connection.close();
        // 注意：如果消费没有绑定交换机和队列，则消息会丢失

    }
}