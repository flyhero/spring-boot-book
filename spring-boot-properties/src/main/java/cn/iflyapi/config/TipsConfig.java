package cn.iflyapi.config;

import cn.iflyapi.properties.TipsProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Author: qfwang
 * Date: 2017-12-07 下午2:25
 */
@Configuration
@EnableConfigurationProperties({TipsProperties.class})
public class TipsConfig {
}
