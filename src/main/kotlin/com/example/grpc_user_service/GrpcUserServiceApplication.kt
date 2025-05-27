package com.example.grpc_user_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GrpcUserServiceApplication

fun main(args: Array<String>) {
	runApplication<GrpcUserServiceApplication>(*args)
}
