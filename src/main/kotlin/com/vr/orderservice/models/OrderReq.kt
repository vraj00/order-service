package com.vr.orderservice.models

data class OrderReq(private val fruits: String) {
    fun getFruits(): String {
        return fruits
    }
}
