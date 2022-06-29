package com.guava3s.client;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

import java.time.Clock;
import java.util.concurrent.CountDownLatch;

/**
 * @author Micolen
 * @version 1.0
 * @ClassName CoapClient
 * @mail 2168626265@qq.com
 */
public class CoapClientTest {

    public static void main(String[] args) throws InterruptedException {
        int count = 100;
        CountDownLatch countDown = new CountDownLatch(count);
        long start = Clock.systemUTC().millis();
        CoapClient client = new CoapClient("coap://127.0.0.1:5683/south_test/lenovo?appKey=zq6NDc3sb6QmoQF1&appSecret=PosmJNUoMLD777Nf7tlu");
        for (int i = 0; i < count; i++) {

            client.post(new CoapHandler() {

                @Override
                public void onLoad(CoapResponse response) {
                    System.out.println(Utils.prettyPrint(response));
                    countDown.countDown();
                }

                @Override
                public void onError() {

                }
            }, "payload message", MediaTypeRegistry.TEXT_PLAIN);
        }
        countDown.await();
        long end = Clock.systemUTC().millis();
        System.out.println(end - start);

    }
}
