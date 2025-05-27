package com.example.grpc_user_service.service

import com.example.grpc_user_service.service.handler.UserInformationRequestHandler
import com.vinsguru.user.*
import io.grpc.stub.StreamObserver
import org.springframework.grpc.server.service.GrpcService

@GrpcService
class UserService(
    val userInformationRequestHandler: UserInformationRequestHandler
) : UserServiceGrpc.UserServiceImplBase() {

    override fun getUserInformation(
        request: UserInformationRequest,
        responseObserver: StreamObserver<UserInformation>
    ) {
        val userInformation = userInformationRequestHandler.getUserInformation(request)
        responseObserver.onNext(userInformation)
        responseObserver.onCompleted()
    }

    override fun tradeStock(request: StockTradeRequest?, responseObserver: StreamObserver<StockTradeResponse>?) {
        super.tradeStock(request, responseObserver)
    }
}