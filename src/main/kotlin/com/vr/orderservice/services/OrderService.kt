package com.vr.orderservice.services

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.text.DecimalFormat

@Service
class OrderService(private val pricingService: PricingService) {

    fun placeOrder(fruits: List<String>): ResponseEntity<String> {
        if (fruits.isNullOrEmpty()) {
            return ResponseEntity.badRequest().body("No fruits ordered!!!")
        }

        val total = pricingService.calculateTotal(fruits)
        val df = DecimalFormat("#,##0.00")

        return ResponseEntity.ok().body("Total amount for order: ${fruits.joinToString()} = ${df.format(total)}$")

    }
}