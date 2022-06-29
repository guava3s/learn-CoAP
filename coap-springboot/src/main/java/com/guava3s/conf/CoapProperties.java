package com.guava3s.conf;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Micolen
 * @version 1.0
 * @ClassName CoapProperties
 * @mail 2168626265@qq.com
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.coap")
public class CoapProperties {

    private String hostName;

    private Integer port;

}
