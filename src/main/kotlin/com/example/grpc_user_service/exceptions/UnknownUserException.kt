package com.example.grpc_user_service.exceptions

class UnknownUserException(val userId: Int) : RuntimeException(message = "User $userId is not found")