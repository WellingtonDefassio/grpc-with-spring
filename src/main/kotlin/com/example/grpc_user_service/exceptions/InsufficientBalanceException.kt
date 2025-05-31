package com.example.grpc_user_service.exceptions

class InsufficientBalanceException(userId: Int?) : RuntimeException("User $userId does not have enough fund to complete the transaction")