package com.vr.orderservice.services

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Service
class OrderService(private val pricingService: PricingService,
                   private val publishService: OrderEventPublisher) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun placeOrder(fruits: List<String>, hasDiscount: Boolean): ResponseEntity<String> {
        if (fruits.isNullOrEmpty()) {
            return ResponseEntity.badRequest().body("No fruits ordered!!!")
        }

        val total = pricingService.calculateTotal(fruits, hasDiscount)
        val df = DecimalFormat("#,##0.00")

        val message = "Order successful; total amount: "+df.format(total) + "$"
        publishService.publisher(message, fruits.joinToString ( "," ), getDeliveryDate())

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

}