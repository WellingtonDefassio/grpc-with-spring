package com.example.grpc_user_service.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "customer")
data class User(
    @Id
    val id: Int,
    val name: String,
    val balance: Int = 0
)