package com.example.grpc_user_service.exceptions

class UnknownTickerException(val userId: Int) : RuntimeException(message = "Ticker is not found")