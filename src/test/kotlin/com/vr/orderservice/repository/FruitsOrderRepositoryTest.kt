package com.vr.orderservice.repository

import com.vr.orderservice.models.FruitsOrder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class FruitsOrderRepositoryTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val fruitsOrderRepo: FruitsOrderRepository) {

    @Test
    fun findByName() {
        val fruitOrder = FruitsOrder("FRUIT", 0.60, 10)
        entityManager.persist(fruitOrder)
        entityManager.flush()
        val fruitFound = fruitsOrderRepo.findByFruitName("FRUIT")
        Assertions.assertEquals("FRUIT", fruitFound.fruitName)
        Assertions.assertEquals(10, fruitFound.fruitQty)
    }
}