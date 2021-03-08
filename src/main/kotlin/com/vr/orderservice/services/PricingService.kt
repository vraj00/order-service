package com.vr.orderservice.services

import com.vr.orderservice.models.Discount
import com.vr.orderservice.models.FruitsOrder
import com.vr.orderservice.repository.DiscountRepository
import com.vr.orderservice.repository.FruitsOrderRepository
import org.springframework.stereotype.Service

@Service
class PricingService(private val repo: FruitsOrderRepository,
                     private val discountRepo: DiscountRepository) {

    /**
     * Total amount calculation for placed order
     */
    fun calculateTotal(fruits: List<String>,
                       hasDiscount: Boolean) : Double {

        var total = 0.00
        //Calculate total without discount
        if (!hasDiscount) {
            val priceMap = getPriceMap()
            for (fruit in fruits) {
                if (priceMap.containsKey(fruit)) {
                    total += priceMap[fruit]!!
                }
            }
        } else {
            //Total with discount
            total = calculateDiscountedTotal(fruits)
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

    fun getFruitsMapFromOrder(fruits: List<String>): HashMap<String, Int> {
        val fruitMap = HashMap<String, Int>()
        for (fruit in fruits) {
            if (fruitMap.containsKey(fruit)) {
                fruitMap[fruit] = fruitMap[fruit]!! + 1
            } else {
                fruitMap[fruit] = 1
            }
        }
        return fruitMap
    }

    fun calculateDiscountedTotal(fruits: List<String>) : Double {
        val fruitMap = getFruitsMapFromOrder(fruits)
        val priceMap = getPriceMap()
        val fruitDiscounts : List<Discount> = discountRepo.findAll() as List<Discount>
        val discountMap : HashMap<String, Discount> = HashMap()
        var total = 0.00

        for (fruitDsct in fruitDiscounts) {
            discountMap[fruitDsct.fruitName] = fruitDsct
        }

        fruitMap.forEach { (key, value) ->
            val discQty = discountMap[key]?.DiscountQty
            val discPrice = discountMap[key]?.DiscountPrice
            // Eligible for discount
            if (discQty?.compareTo(value)!! <= 0) {
                total += discPrice?.times(value/discQty)!! + priceMap[key]!!.times(value % discQty)
            } else {
                total += priceMap[key]!!.times(value)
            }
        }

        return total

    }

}