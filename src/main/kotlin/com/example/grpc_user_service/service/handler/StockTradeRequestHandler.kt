package com.example.grpc_user_service.service.handler

import com.example.grpc_user_service.entity.PortfolioItem
import com.example.grpc_user_service.exceptions.InsufficientBalanceException
import com.example.grpc_user_service.exceptions.UnknownTickerException
import com.example.grpc_user_service.exceptions.UnknownUserException
import com.example.grpc_user_service.repository.PortfolioItemRepository
import com.example.grpc_user_service.repository.UserRepository
import com.example.grpc_user_service.util.EntityMessageMapper
import com.vinsguru.common.Ticker
import com.vinsguru.user.StockTradeRequest
import com.vinsguru.user.StockTradeResponse
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service
class StockTradeRequestHandler(
    val userRepository: UserRepository,
    val portfolioItemRepository: PortfolioItemRepository
) {

    @Transactional
    fun buyStock(request: StockTradeRequest): StockTradeResponse {
        //validate
        validateTicker(request.ticker)
        val user = userRepository.findById(request.userId).orElseThrow { UnknownUserException(request.userId) }
        val totalPrice = request.quantity * request.price
        validateUserBalance(user.id, user.balance, totalPrice)
        //valid request
        val userToSave = user.apply {
            balance = user.balance - totalPrice
        }
        val portfolio = validatePortfolio(portfolioItemRepository.findByUserIdAndTicker(userToSave.id!!, request.ticker), request)
        userRepository.save(userToSave)
        portfolioItemRepository.save(portfolio)

        return EntityMessageMapper.toStockTradeResponse(request, userToSave.balance)
    }

    @Transactional
    fun sellStock(request: StockTradeRequest): StockTradeResponse {
        //validate
        validateTicker(request.ticker)
        val user = userRepository.findById(request.userId).orElseThrow { UnknownUserException(request.userId) }

        val portfolioItem = portfolioItemRepository.findByUserIdAndTicker(user.id!!, request.ticker) ?: throw UnknownTickerException()
        val portfolioToSave = validateSellPortfolio(portfolioItem, request, user.id!!)

        val totalPrice = request.quantity * request.price

        val userToSave = user.apply {
            balance = user.balance + totalPrice
        }
        userRepository.save(userToSave)
        portfolioItemRepository.save(portfolioToSave)

        return EntityMessageMapper.toStockTradeResponse(request, userToSave.balance)
    }


    fun validateTicker(ticker: Ticker) {
        if (Ticker.UNKNOWN_VALUE == ticker.number) {
            throw UnknownTickerException()
        }
    }

    fun validateUserBalance(userId: Int?, userBalance: Int, totalPrice: Int) {
        if(totalPrice > userBalance) {
            throw InsufficientBalanceException(userId)
        }
    }

    fun validatePortfolio(portfolio: PortfolioItem?, request: StockTradeRequest): PortfolioItem {
        return portfolio?.apply {
            quantity = (portfolio.quantity?.plus(request.quantity))
        }
            ?: EntityMessageMapper.toPortfolioItem(request)
    }

    fun validateSellPortfolio(portfolioItem: PortfolioItem, request: StockTradeRequest, userId: Int): PortfolioItem {
        if(request.quantity > portfolioItem.quantity!!) {
            throw InsufficientBalanceException(userId)
        }
        return portfolioItem.apply {
            quantity = portfolioItem.quantity!! - request.quantity
        }
    }
}