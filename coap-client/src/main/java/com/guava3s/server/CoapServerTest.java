package com.guava3s.server;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.elements.tcp.TcpServerConnector;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.DoubleStream;

/**
 * @author Micolen
 * @version 1.0
 * @ClassName CoapServerTest
 * @date 2022/6/29 14:49
 * @mail 2168626265@qq.com
 */
public class CoapServerTest {

    /**
     * 服务器地址
     */
    private static final String HOST_NAME = "127.0.0.1";

    /**
     * 端口号
     */
    private static final Integer PORT = 5683;


    public static void main(String[] args) {
        final CoapServer server = new CoapServer();

        // 默认就是ucp实现传输层
        server.addEndpoint(new CoapEndpoint(new InetSocketAddress(HOST_NAME, PORT)));

        // 加入tcp实现传输层
        server.addEndpoint(new CoapEndpoint(
                new TcpServerConnector(new InetSocketAddress(HOST_NAME, PORT), 4, 20000),
                NetworkConfig.getStandard()));

//      可以加入dtls支持，也就是coaps
//		server.addEndpoint(new CoapEndpoint(
//				new DTLSConnector(), //这里只是抛砖引玉，需要构建DtlsConnectorConfig
//				NetworkConfig.getStandard()));

        server.add(new CoapResource("south_test") {
            @Override
            public void handleGET(CoapExchange exchange) {
                String query = exchange.getQueryParameter("query");
                if (query.equals("temperature")) {
                    exchange.respond("south_test GET 请求返回值为：" + 25.3 + "度");
                } else {
                    exchange.respond("无查询字段");
                }
            }

            @Override
            public void handlePOST(CoapExchange exchange) { // 1
                exchange.respond("south_test POST请求 无返回值");
            }
        }.add(new CoapResource("temperature") {
            @Override
            public void handlePOST(CoapExchange exchange) {  //2
                exchange.respond("south_test/temperature POST请求返回值为：" + 23.5 + "度");
            }
        }));
        Executors.newSingleThreadExecutor().execute(() -> {
            server.start();
            System.out.println("服务器于" + new Date() + "启动");
        });
    }
}
