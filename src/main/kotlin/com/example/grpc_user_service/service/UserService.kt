package com.example.grpc_user_service.service

import com.example.grpc_user_service.service.handler.StockTradeRequestHandler
import com.example.grpc_user_service.service.handler.UserInformationRequestHandler
import com.vinsguru.user.*
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.slf4j.LoggerFactory

@GrpcService
class UserService(
    val userInformationRequestHandler: UserInformationRequestHandler,
    val stockTradeRequestHandler: StockTradeRequestHandler
) : UserServiceGrpc.UserServiceImplBase() {

    val logger = LoggerFactory.getLogger(UserService::class.java)

    override fun getUserInformation(
        request: UserInformationRequest,
        responseObserver: StreamObserver<UserInformation>
    ) {
        logger.info("user information for id {}", request.userId)
        val userInformation = userInformationRequestHandler.getUserInformation(request)
        responseObserver.onNext(userInformation)
        responseObserver.onCompleted()
    }

    override fun tradeStock(request: StockTradeRequest, responseObserver: StreamObserver<StockTradeResponse>) {
        logger.info("trade stock request: {}", request)
        if (TradeAction.SELL == request.action) {
            val sellStock = stockTradeRequestHandler.sellStock(request)
            responseObserver.onNext(sellStock)
        }
        if (TradeAction.BUY == request.action) {
            val buyStock = stockTradeRequestHandler.buyStock(request)
            responseObserver.onNext(buyStock)
        }
        responseObserver.onCompleted()
    }
}