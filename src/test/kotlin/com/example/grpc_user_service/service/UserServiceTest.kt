package com.example.grpc_user_service.service

import com.vinsguru.common.Ticker
import com.vinsguru.user.StockTradeRequest
import com.vinsguru.user.TradeAction
import com.vinsguru.user.UserInformationRequest
import com.vinsguru.user.UserServiceGrpc
import io.grpc.StatusRuntimeException
import net.devh.boot.grpc.client.inject.GrpcClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class UserServiceTest {

    @GrpcClient("user-service")
    private lateinit var stubClient: UserServiceGrpc.UserServiceBlockingStub

    @Test
    fun `'getUserInformation' should return correct values for existing user`() {
        val request = UserInformationRequest.newBuilder()
            .setUserId(1)
            .build()

        val response = stubClient.getUserInformation(request)

        assertThat(response.userId).isEqualTo(1)
        assertThat(response.name).isEqualTo("Sam")
        assertThat(response.balance).isEqualTo(10_000)
        assertThat(response.holdingsList).isEmpty()
    }

    @Test
    fun `'getUserInformation' should throws when a user is not found`() {
        val request = UserInformationRequest.newBuilder()
            .setUserId(969)
            .build()

        val throws = assertThrows<StatusRuntimeException> { stubClient.getUserInformation(request) }
        assertThat(throws.message).isEqualTo("NOT_FOUND: User 969 is not found")
    }

    @Test
    fun `'tradeStock' should throws when a unknown ticker is provided`() {
        val request = StockTradeRequest.newBuilder()
            .setUserId(1)
            .setTicker(Ticker.UNKNOWN)
            .setQuantity(1)
            .setPrice(10)
            .build()

        val throws = assertThrows<StatusRuntimeException> { stubClient.tradeStock(request) }
        assertThat(throws.message).isEqualTo("INVALID_ARGUMENT: Ticker is not found")
    }

    @Test
    fun `'tradeStock' should throws when a try to sell insufficient shares`() {
        val request = StockTradeRequest.newBuilder()
            .setUserId(1)
            .setTicker(Ticker.APPLE)
            .setQuantity(100)
            .setPrice(10)
            .setAction(TradeAction.SELL)
            .build()

        val throws = assertThrows<StatusRuntimeException> { stubClient.tradeStock(request) }
        assertThat(throws.message).isEqualTo("INVALID_ARGUMENT: Ticker is not found")
    }

    @Test
    fun `'tradeStock' should throws when a try to buy more then balance`() {
        val request = StockTradeRequest.newBuilder()
            .setUserId(1)
            .setTicker(Ticker.APPLE)
            .setQuantity(100)
            .setPrice(10_001)
            .setAction(TradeAction.BUY)
            .build()

        val throws = assertThrows<StatusRuntimeException> { stubClient.tradeStock(request) }
        assertThat(throws.message).isEqualTo("FAILED_PRECONDITION: User 1 does not have enough fund to complete the transaction")
    }

    @Test
    fun `'integration' should be able to buy and sell tickets if all parameters is correct`(){
        val requestBuy = StockTradeRequest.newBuilder()
            .setUserId(1)
            .setTicker(Ticker.APPLE)
            .setQuantity(100)
            .setPrice(10)
            .setAction(TradeAction.BUY)
            .build()

        val requestSell = StockTradeRequest.newBuilder()
            .setUserId(1)
            .setTicker(Ticker.APPLE)
            .setQuantity(50)
            .setPrice(10)
            .setAction(TradeAction.SELL)
            .build()

        val requestUser = UserInformationRequest.newBuilder()
            .setUserId(1)
            .build()

        val user = stubClient.getUserInformation(requestUser)

        stubClient.tradeStock(requestBuy)
        val userInformation = stubClient.getUserInformation(requestUser)

        assertThat(userInformation.balance).isEqualTo(9_000)
        assertThat(userInformation.holdingsList.size).isEqualTo(1)
        assertThat(userInformation.holdingsList[0].ticker).isEqualTo(Ticker.APPLE)
        assertThat(userInformation.holdingsList[0].quantity).isEqualTo(100)

        stubClient.tradeStock(requestSell)

        val userInformationAtt = stubClient.getUserInformation(requestUser)

        assertThat(userInformationAtt.balance).isEqualTo(9_500)
        assertThat(userInformationAtt.holdingsList.size).isEqualTo(1)
        assertThat(userInformationAtt.holdingsList[0].ticker).isEqualTo(Ticker.APPLE)
        assertThat(userInformationAtt.holdingsList[0].quantity).isEqualTo(50)

    }
}