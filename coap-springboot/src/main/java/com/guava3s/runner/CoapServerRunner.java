package com.guava3s.runner;

import com.guava3s.conf.CoapProperties;
import com.guava3s.controller.TestController;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.elements.tcp.TcpServerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Micolen
 * @version 1.0
 * @ClassName CoapServerRunner
 * @mail 2168626265@qq.com
 */
@Slf4j
@Component
public class CoapServerRunner implements ApplicationRunner {

    private static AtomicInteger count = new AtomicInteger();

    @Autowired
    private CoapProperties coapProperties;

    @Autowired
    private TestController testController;

    @Override
    public void run(ApplicationArguments args) {
        final CoapServer server = new CoapServer();

        // 默认就是ucp实现传输层
        server.addEndpoint(new CoapEndpoint(new InetSocketAddress(coapProperties.getHostName(), coapProperties.getPort())));

        // 加入tcp实现传输层
        server.addEndpoint(new CoapEndpoint(
                new TcpServerConnector(new InetSocketAddress(coapProperties.getHostName(), coapProperties.getPort()), 4, 20000),
                NetworkConfig.getStandard()));

//      可以加入dtls支持，也就是coaps
//		server.addEndpoint(new CoapEndpoint(
//				new DTLSConnector(), //这里只是抛砖引玉，需要构建DtlsConnectorConfig
//				NetworkConfig.getStandard()));


        // 添加路径探测
        server.add(testController.getSouth_test1().add(testController.getNode_1_temperature()));
        Executors.newSingleThreadExecutor().execute(() -> {
            server.start();
            log.info("CoAP服务器于 {} 启动", new Date());
        });
    }

}
