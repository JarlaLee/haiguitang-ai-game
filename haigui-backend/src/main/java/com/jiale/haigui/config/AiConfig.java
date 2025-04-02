package com.jiale.haigui.config;

import com.volcengine.ark.runtime.service.ArkService;
import lombok.Data;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

// Configuration 也是 Component
@Component
@ConfigurationProperties(prefix = "ai")
@Data
public class AiConfig {

    private String apiKey;
    private String baseUrl;


//    /**
//     * 初始化 AI 客户端
//     * arkService() 方法定义了一个叫做 arkService 的bean，并将其注入到Spring容器中。
//     * 在另一个类中，您可以使用 @Resource 来注入 ArkService
//     * 告诉Spring容器，arkService 方法是一个工厂方法，用于创造ArkService 实例。
//     * @return
//     */
//    @Bean
//    public ArkService arkService() {
//        // 此为默认路径，您可根据业务所在地域进行配置
//        String baseUrl = "https://ark.cn-beijing.volces.com/api/v3";
//        ConnectionPool connectionPool = new ConnectionPool(5, 1, TimeUnit.SECONDS);
//        Dispatcher dispatcher = new Dispatcher();
//        ArkService service = ArkService.builder().dispatcher(dispatcher)
//                .connectionPool(connectionPool)
//                .baseUrl(baseUrl)
//                .apiKey(apiKey)
//                .build();
//        return service;
//    }
}
