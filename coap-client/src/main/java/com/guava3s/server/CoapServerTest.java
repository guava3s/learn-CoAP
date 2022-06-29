package com.guava3s.server;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.elements.tcp.TcpServerConnector;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Micolen
 * @version 1.0
 * @ClassName CoapServerTest
 * @date 2022/6/29 14:49
 * @mail 2168626265@qq.com
 */
public class CoapServerTest {

    private static AtomicInteger count = new AtomicInteger();

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
                System.out.println("handleGET方法执行");
                handlePOST(exchange);
            }

            @Override
            public void handlePOST(CoapExchange exchange) { // 1
                System.out.println("exchange.getRequestOptions().getUriQueryString():" + exchange.getRequestOptions().getUriQueryString());
                System.out.println("exchange.getRequestText().length(): " + exchange.getRequestText().length());
                exchange.respond("asdfasdf");
            }
        }.add(new CoapResource("lenovo") {
            @Override
            public void handlePOST(CoapExchange exchange) {  //2
                int c = count.incrementAndGet();
                if (c % 10000 == 0) {
                    System.out.println(c);
                }
                exchange.respond(String.valueOf(c));
            }
        }));
        Executors.newSingleThreadExecutor().execute(() -> {
            server.start();
            System.out.println("服务器于" + new Date() + "启动");
        });
    }
}
