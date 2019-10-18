package com.cp.test3;

import com.cp.util.MQConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Description: 发布订阅
 * 邮件消费者
 * @author chenpeng
 * @date 2019/10/18 10:31
 */
public class ConsumerEmailFanout {
    private static final String QUEUE_NAME = "consumerFanout_email";
    private static final String EXCHANGE_NAME = "fanout_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("邮件消费者启动");
        Connection connection = MQConnectionUtils.newConnection();
        Channel channel = connection.createChannel();
        // 3.消费者关联队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 4.队列交换机绑定 参数1 队列 参数2交换机 参数3 routingKey
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME,"");
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("消费者获取生产者消息:" + msg);
            }
        };
        // 5.消费者监听队列消息
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }

}