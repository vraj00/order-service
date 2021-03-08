package com.vr.orderservice.controller

import com.vr.orderservice.models.OrderReq
import com.vr.orderservice.services.OrderService
import org.springframework.web.bind.annotation.*
import kotlin.streams.toList

@RestController
@RequestMapping("/")
class OrderController(private val orderService: OrderService) {

    @PostMapping("orderFruits")
    fun orderFruits(@RequestBody order: OrderReq): String {
        var fruits: List<String> = ArrayList()
        if ( order.getFruits().isNotEmpty()) {
            fruits = order.getFruits().split(",").stream().map{item -> item.toUpperCase()}.toList()
        }
        val response = orderService.placeOrder(fruits)
        return response.toString()
    }

}