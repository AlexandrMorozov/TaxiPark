package com.taxipark.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer
{
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new OrderMessageHandler(),"/order")
                .setAllowedOrigins("*");

    }

    @Bean
    public TaskScheduler taskScheduler()
    {
        TaskScheduler scheduler = new ThreadPoolTaskScheduler();

        //scheduler.
        //scheduler.setPoolSize(2);
       // scheduler.setThreadNamePrefix("scheduled-task-");
        //scheduler.setDaemon(true);

        return scheduler;
    }
}
