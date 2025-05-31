package com.example.grpc_user_service.config

import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Executors

@Configuration
class ServerConfiguration {

    @Bean
    fun serverConfig(): GrpcServerConfigurer {
        return GrpcServerConfigurer { serverBuilder ->
            serverBuilder.executor(Executors.newVirtualThreadPerTaskExecutor())
        }
    }
}