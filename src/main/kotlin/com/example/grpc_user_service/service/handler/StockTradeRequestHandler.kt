package com.example.grpc_user_service.service.handler

import com.example.grpc_user_service.exceptions.UnknownUserException
import com.example.grpc_user_service.repository.PortfolioItemRepository
import com.example.grpc_user_service.util.EntityMessageMapper
import com.example.grpc_user_service.repository.UserRepository
import com.vinsguru.user.StockTradeRequest
import com.vinsguru.user.StockTradeResponse
import com.vinsguru.user.UserInformation
import com.vinsguru.user.UserInformationRequest
import org.springframework.stereotype.Service


@Service
class StockTradeRequestHandler(
    val userRepository: UserRepository,
    val portfolioItemRepository: PortfolioItemRepository
) {

    fun buyStock(stockRequest: StockTradeRequest): StockTradeResponse {
        //validate

        //update
    }
}