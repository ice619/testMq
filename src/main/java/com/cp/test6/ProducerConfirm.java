package com.cp.test6;

import com.cp.util.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Description: Confirm 模式
 *
 * @author chenpeng
 * @date 2019/10/18 12:00
 */
public class ProducerConfirm {
    private static final String QUEUE_NAME = "test_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = MQConnectionUtils.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //confirm机制
        channel.confirmSelect();
        String msg = "test_confirmSelect";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        System.out.println("生产者发送消息:" + msg);

        if(!channel.waitForConfirms()){
            System.out.println("消息发送失败!");
        } else {
            System.out.println("消息发送成功!");
        }
        channel.close();
        connection.close();


    }
}