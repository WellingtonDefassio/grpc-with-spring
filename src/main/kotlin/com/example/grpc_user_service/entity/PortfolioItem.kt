package com.example.grpc_user_service.entity

import com.vinsguru.common.Ticker
import jakarta.persistence.*

@Entity
class PortfolioItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int? = null
    @Column(name = "customer_id")
    var userId: Int? = null
    var ticker: Ticker? = null
    var quantity: Int? = null
}