package com.example.grpc_user_service.exceptions

class UnknownTickerException : RuntimeException(message = "Ticker is not found")