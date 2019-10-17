package com.cp.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Description:
 *
 * @author chenpeng
 * @date 2019/10/17 18:19
 */
public class MQConnectionUtils {

    public static Connection newConnection() throws IOException, TimeoutException {
        // 1.定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.72.128");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        return connection;
    }

}