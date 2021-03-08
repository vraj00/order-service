package com.vr.orderservice.repository

import com.vr.orderservice.models.Discount
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class DiscountRepositoryTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val discountRepository: DiscountRepository) {

    @Test
    fun find() {
        val fruitDiscount = Discount(0, "APPLE", 0.60, 2)
        entityManager.persist(fruitDiscount)
        entityManager.flush()
        val discountsFound = discountRepository.findAll() as List<Discount>
        Assertions.assertEquals("APPLE", discountsFound[0].fruitName)
    }
}