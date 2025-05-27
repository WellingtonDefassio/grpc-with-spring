package com.example.grpc_user_service.entity

import com.vinsguru.common.Ticker
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class PortfolioItem(
    @Id
    @GeneratedValue
    val id: Int,
    @Column(name = "customer_id")
    val userId: Int,
    val ticker: Ticker,
    val quantity: Int
)