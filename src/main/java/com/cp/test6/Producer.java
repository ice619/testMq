package com.cp.test6;

import com.cp.util.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Description:RabbitMQ消息确认机制
 * 生产者发送消息出去之后，不知道到底有没有发送到RabbitMQ服务器， 默认是不知道的。
 * 而且有的时候我们在发送消息之后，后面的逻辑出问题了，我们不想要发送之前的消息了，需要撤回该怎么做。
 * 解决方案:
 * 1.AMQP 事务机制
 * 2.Confirm 模式 事务模式:
 * channel.txSelect  将当前channel设置为transaction模式
 * channel.txCommit  提交当前事务
 * channel.txRollback  事务回滚
 * @author chenpeng
 * @date 2019/10/18 11:53
 */
public class Producer {
    private static final String QUEUE_NAME = "test_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MQConnectionUtils.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 将当前管道设置为 txSelect
        // 将当前channel设置为transaction模式 开启事务
        channel.txSelect();

        String msg = "test tx";
        try {
            channel.basicPublish("",QUEUE_NAME, null, msg.getBytes() );
            int i = 1 / 0;
            //提交事务
            channel.txCommit();
            System.out.println("生产者发送消息:" + msg);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("消息进行回滚操作");
            //回滚
            channel.txRollback();
        } finally {
            channel.close();
            connection.close();
        }

    }
}