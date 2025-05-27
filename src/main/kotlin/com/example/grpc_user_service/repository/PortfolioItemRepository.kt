package com.example.grpc_user_service.repository

import com.example.grpc_user_service.entity.PortfolioItem
import com.vinsguru.common.Ticker
import org.springframework.data.jpa.repository.JpaRepository

interface PortfolioItemRepository : JpaRepository<PortfolioItem, Int> {
    fun findAllByUserId(userId: Int): List<PortfolioItem>
    fun findByUserIdAndTicker(userId: Int, ticker: Ticker): PortfolioItem?
}