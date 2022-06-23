package com.nexign.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.tcp.TcpClient;

import java.net.http.HttpClient;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfiguration {
    @Bean
    public WebClient webClientWithTimeout() {
        return WebClient.create();
    }
}