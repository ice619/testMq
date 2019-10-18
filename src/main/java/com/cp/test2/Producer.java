package com.cp.test2;

import com.cp.util.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Description: 工作对列
 *
 * @author chenpeng
 * @date 2019/10/18 10:01
 */
public class Producer {
    private static final String QUEUE_NAME = "test_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MQConnectionUtils.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //设置prefetchCount=1，则Queue每次给每个消费者发送一条消息；消费者处理完这条消息后Queue会再给该消费者发送一条消息。
        channel.basicQos(1);
        for(int i = 0; i < 50; i++){
            String msg = "testMsg" + i;
            System.out.println("生产者发送消息：" + msg);
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        }

        channel.close();
        connection.close();
    }
}