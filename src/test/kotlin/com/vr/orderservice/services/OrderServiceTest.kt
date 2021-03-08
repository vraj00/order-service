package com.vr.orderservice.services

import com.vr.orderservice.models.Discount
import com.vr.orderservice.models.FruitsOrder
import com.vr.orderservice.repository.FruitsOrderRepository
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus

@SpringBootTest
@AutoConfigureTestEntityManager
class OrderServiceTest @Autowired constructor(val entityManager: TestEntityManager,
                       val repo: FruitsOrderRepository,
                       val orderService: OrderService) {

    @Before
    fun setUp() {
        entityManager.persistAndFlush(FruitsOrder("APPLE", 0.60, 10))
        entityManager.persistAndFlush(FruitsOrder("ORANGE", 0.25, 9))

        entityManager.persistAndFlush(Discount(0,"APPLE", 0.60, 2))
        entityManager.persistAndFlush(Discount(0,"ORANGE", 0.50, 3))
    }

    @Test
    fun placeOrder() {
        val response = orderService.placeOrder(listOf("APPLE","APPLE","APPLE","ORANGE"), false)
        assertTrue(response.body!!.contains("2.05$"))
    }

    @Test
    fun placeOrderEmpty() {
        val response = orderService.placeOrder(listOf(), false)
        assertTrue(response.body!!.contains("No fruits ordered"))
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    fun placeOrderWithDiscount() {
        val response = orderService.placeOrder(listOf("APPLE","APPLE","APPLE","ORANGE"), true)
        assertTrue(response.body!!.contains("1.45$"))
        val fruit = repo.findByFruitName("APPLE")
        assertEquals(4, fruit.fruitQty)
    }
}