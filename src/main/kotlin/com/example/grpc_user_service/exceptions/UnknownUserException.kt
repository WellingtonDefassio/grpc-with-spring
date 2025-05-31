package com.example.grpc_user_service.exceptions

class UnknownUserException(val userId: Int) : RuntimeException("User $userId is not found")