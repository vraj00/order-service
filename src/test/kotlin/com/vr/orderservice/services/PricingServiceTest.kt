package com.vr.orderservice.services

import com.vr.orderservice.models.Discount
import com.vr.orderservice.models.FruitsOrder
import com.vr.orderservice.repository.FruitsOrderRepository
import org.junit.Before
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureTestEntityManager
class PricingServiceTest @Autowired constructor(val entityManager: TestEntityManager,
                                                val repo: FruitsOrderRepository,
                                                val pricingService: PricingService) {

    @Before
    fun setUp() {
        entityManager.persistAndFlush(FruitsOrder("APPLE", 0.60, 10))
        entityManager.persistAndFlush(FruitsOrder("ORANGE", 0.25, 9))

        entityManager.persistAndFlush(Discount(0,"APPLE", 0.60, 2))
        entityManager.persistAndFlush(Discount(0,"ORANGE", 0.50, 3))
    }

    @Test
    fun calculateTotal() {
        val total = pricingService.calculateTotal(listOf("APPLE","APPLE","APPLE","ORANGE"), false)
        Assertions.assertEquals(2.05, total)
    }

    @Test
    fun calculateDiscountedTotal() {
        val total = pricingService.calculateTotal(listOf("APPLE","APPLE","APPLE","ORANGE"), true)
        Assertions.assertEquals(1.45, total)
    }

}