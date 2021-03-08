package com.vr.orderservice.services

import com.vr.orderservice.repository.FruitsOrderRepository
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Service
class OrderService(private val pricingService: PricingService,
                   private val publishService: OrderEventPublisher,
                   private val repo : FruitsOrderRepository) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun placeOrder(fruits: List<String>, hasDiscount: Boolean): ResponseEntity<String> {
        if (fruits.isNullOrEmpty()) {
            return ResponseEntity.badRequest().body("No fruits ordered!!!")
        }

        if (!checkInventory(fruits)) {
            // Limited stock
            publishService.publisher("Limited Stock Order Failure", fruits.joinToString(","), "")
            return ResponseEntity.ok().body("Limited Stock")
        }

        val total = pricingService.calculateTotal(fruits, hasDiscount)
        val df = DecimalFormat("#,##0.00")

        if (total == 0.00) {
            publishService.publisher("Order Failure for", fruits.joinToString(","), "")
            return ResponseEntity.ok().body("Order Failure")
        } else {
            if (!updateInventory(fruits)) {
                publishService.publisher("Order Failure for", fruits.joinToString(","), "")
                return ResponseEntity.ok().body("Order Failure")
            }
            val message = "Order successful; total amount: "+df.format(total) + "$"
            publishService.publisher(message, fruits.joinToString ( "," ), getDeliveryDate())
        }

        return ResponseEntity.ok().body("Order successful-Total amount for order:" +
                " ${fruits.joinToString()} = ${df.format(total)}$")

    }

    /**
     * Estimated delivery date calculation
     */
    fun getDeliveryDate(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        return current.plusDays(1).format(formatter)
    }

    /**
     * Check Inventory for limited stock
     */
    fun checkInventory(fruits : List<String>): Boolean {
        val fruitMap = pricingService.getFruitsMapFromOrder(fruits)

        for ((key, value) in fruitMap) {
            try {
                if (repo.findByFruitName(key)?.fruitQty < value) {
                    return false
                }
            } catch ( exception: Exception) {
                logger.error(exception.message)
            }
        }

        return true
    }

    /**
     * Update Inventory
     */
    fun updateInventory(fruits : List<String>): Boolean {
        val fruitMap = pricingService.getFruitsMapFromOrder(fruits)

        for ((key, value) in fruitMap) {
            try {
                val fruit = repo.findByFruitName(key)
                fruit.fruitQty -= value
                repo.save(fruit)
            } catch ( exception: Exception) {
                logger.error(exception.message)
                return false
            }
        }
        return true
    }

}