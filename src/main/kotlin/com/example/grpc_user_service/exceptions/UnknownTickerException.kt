package com.example.grpc_user_service.exceptions

class UnknownTickerException : RuntimeException("Ticker is not found")