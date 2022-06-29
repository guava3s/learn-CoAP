package com.guava3s.controller;

import lombok.Data;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.DoubleStream;

/**
 * @author Micolen
 * @version 1.0
 * @ClassName TestController
 * @mail 2168626265@qq.com
 */
@Data
@Component
public class TestController {

    private static final String TEST_ROOT = "south";
    private static final String NODE_1_TEMPERATURE = "temperature";


    /**
     * 一级目录
     */
    private CoapResource south_test1 = new CoapResource(TEST_ROOT) {
        @Override
        public void handlePOST(CoapExchange exchange) { // 1
            System.out.println("exchange.getRequestOptions().getUriQueryString():" + exchange.getRequestOptions().getUriQueryString());
            System.out.println("exchange.getRequestText().length(): " + exchange.getRequestText().length());
            exchange.respond("post 执行完毕");
        }
    };

    /**
     * 二级目录
     */
    private CoapResource node_1_temperature = new CoapResource(NODE_1_TEMPERATURE) {
        @Override
        public void handlePOST(CoapExchange exchange) {  //2
            Random random = new Random();
            DoubleStream doubles = random.doubles(20d, 32d);
            exchange.respond("当前温度为:" + doubles.toString() + "度");
        }
    };


}
