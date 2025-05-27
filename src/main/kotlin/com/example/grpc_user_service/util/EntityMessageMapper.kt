package com.example.grpc_user_service.util

import com.example.grpc_user_service.entity.PortfolioItem
import com.example.grpc_user_service.entity.User
import com.vinsguru.user.Holding
import com.vinsguru.user.UserInformation

class EntityMessageMapper {
    companion object {
        fun toUserInformation(user: User, items: List<PortfolioItem>): UserInformation {
            val holdingList = items.map {
                Holding.newBuilder()
                    .setTicker(it.ticker)
                    .setQuantity(it.quantity)
                    .build()
            }
            return UserInformation.newBuilder()
                .setUserId(user.id)
                .setName(user.name)
                .setBalance(user.balance)
                .addAllHoldings(holdingList)
                .build()
        }
    }
}