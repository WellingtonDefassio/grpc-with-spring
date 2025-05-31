package com.example.grpc_user_service.util

import com.example.grpc_user_service.entity.PortfolioItem
import com.example.grpc_user_service.entity.User
import com.vinsguru.user.Holding
import com.vinsguru.user.StockTradeRequest
import com.vinsguru.user.StockTradeResponse
import com.vinsguru.user.UserInformation

class EntityMessageMapper {
    companion object {
        fun toUserInformation(user: User, items: List<PortfolioItem>): UserInformation {
            val holdingList = items.map {
                Holding.newBuilder()
                    .setTicker(it.ticker)
                    .setQuantity(it.quantity!!)
                    .build()
            }
            return UserInformation.newBuilder()
                .setUserId(user.id!!)
                .setName(user.name)
                .setBalance(user.balance)
                .addAllHoldings(holdingList)
                .build()
        }
        fun toPortfolioItem(request: StockTradeRequest): PortfolioItem {
            return PortfolioItem().apply {
                ticker = request.ticker
                quantity = request.quantity
                userId = request.userId
            }
        }

        fun toStockTradeResponse(request: StockTradeRequest, balance: Int): StockTradeResponse {
            return StockTradeResponse.newBuilder()
                .setUserId(request.userId)
                .setPrice(request.price)
                .setTicker(request.ticker)
                .setQuantity(request.quantity)
                .setAction(request.action)
                .setTotalPrice(request.price * request.quantity)
                .setBalance(balance)
                .build()
        }
    }
}