package com.vr.orderservice

import com.vr.orderservice.models.FruitsOrder
import com.vr.orderservice.repository.FruitsOrderRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
class OrderserviceApplicationTests(
    @Autowired
    val repo: FruitsOrderRepository
) {

    @Test
    fun loadData() {
        repo.save(FruitsOrder("APPLE", 0.60, 10))
        val fruit: FruitsOrder = repo.findByFruitName("APPLE")
        print(fruit.fruitQty)
        Assertions.assertTrue(fruit.fruitQty == 10)
        repo.deleteAll()
    }

}
