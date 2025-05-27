package com.example.grpc_user_service.service.handler

import com.example.grpc_user_service.exceptions.UnknownUserException
import com.example.grpc_user_service.repository.PortfolioItemRepository
import com.example.grpc_user_service.util.EntityMessageMapper
import com.example.grpc_user_service.repository.UserRepository
import com.vinsguru.user.UserInformation
import com.vinsguru.user.UserInformationRequest
import org.springframework.stereotype.Service


@Service
class UserInformationRequestHandler(
    val userRepository: UserRepository,
    val portfolioItemRepository: PortfolioItemRepository
) {

    fun getUserInformation(request: UserInformationRequest): UserInformation {
        val user = userRepository.findById(request.userId).orElseThrow { UnknownUserException(request.userId) }
        val portfolioItems = portfolioItemRepository.findAllByUserId(request.userId)

        return EntityMessageMapper.toUserInformation(user, portfolioItems)
    }
}