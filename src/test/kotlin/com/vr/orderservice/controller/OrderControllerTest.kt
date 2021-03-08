package com.vr.orderservice.controller

import com.vr.orderservice.models.FruitsOrder
import com.vr.orderservice.repository.FruitsOrderRepository
import com.vr.orderservice.services.OrderService
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus

@SpringBootTest
class OrderControllerTest( @Autowired private val orderService: OrderService) {

    @Before
    fun setUp() {
        val repo: FruitsOrderRepository = Mockito.mock(FruitsOrderRepository::class.java)
        Mockito.`when`(repo.findAll())
            .thenReturn(
                listOf(
                    FruitsOrder("APPLE", 0.60, 10),
                    FruitsOrder("ORANGE", 0.25, 9)
                )
            )
    }

    @Test
    fun orderTest() {
        val response = orderService.placeOrder(listOf("APPLE", "APPLE", "ORANGE"))
        assertEquals(HttpStatus.OK, response.statusCode)
    }
}
