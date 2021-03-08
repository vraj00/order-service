package com.vr.orderservice.services

import com.vr.orderservice.models.FruitsOrder
import com.vr.orderservice.repository.FruitsOrderRepository
import org.springframework.stereotype.Service

@Service
class PricingService(private val repo: FruitsOrderRepository) {

    /**
     * Total amount calculation for placed order
     */
    fun calculateTotal(fruits: List<String>) : Double {

        var total = 0.00
        val priceMap = getPriceMap()
        for (fruit in fruits) {
            if (priceMap.containsKey(fruit)) {
                total += priceMap[fruit]!!
            }
        }

        return total
    }

    fun getPriceMap() : HashMap<String, Double> {
        val fruits: List<FruitsOrder> = repo.findAll() as List<FruitsOrder>
        val priceMap : HashMap<String, Double> = HashMap()
        for (fruit : FruitsOrder in fruits) {
            print("Item : ${fruit.fruitName}")
            print(" Price : ${fruit.fruitPrice}")
            println(" Qty : ${fruit.fruitQty}")
            priceMap[fruit.fruitName] = fruit.fruitPrice
        }

        return priceMap
    }

}