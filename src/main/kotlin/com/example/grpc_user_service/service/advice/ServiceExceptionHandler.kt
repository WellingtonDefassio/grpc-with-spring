package com.example.grpc_user_service.service.advice

import com.example.grpc_user_service.exceptions.InsufficientBalanceException
import com.example.grpc_user_service.exceptions.UnknownTickerException
import com.example.grpc_user_service.exceptions.UnknownUserException
import io.grpc.Status
import net.devh.boot.grpc.server.advice.GrpcAdvice
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler

@GrpcAdvice
class ServiceExceptionHandler {

    @GrpcExceptionHandler(UnknownTickerException::class)
    fun handleInvalidArguments(e: UnknownTickerException): Status {
        return Status.INVALID_ARGUMENT.withDescription(e.message)
    }

    @GrpcExceptionHandler(UnknownUserException::class)
    fun unknownUserException(e: UnknownUserException): Status {
        return Status.NOT_FOUND .withDescription(e.message)
    }

    @GrpcExceptionHandler(InsufficientBalanceException::class)
    fun insufficientBalanceException(e: InsufficientBalanceException): Status {
        return Status.FAILED_PRECONDITION .withDescription(e.message)
    }

}