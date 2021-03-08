package com.vr.orderservice.services

import org.springframework.context.ApplicationEvent

class OrderCompleteEvent(source : Any
                         , private var message: String
                         , private var fruits: String, private var deliveryDate: String) : ApplicationEvent(source) {

    fun getMessage(): String? {
        return message
    }

    fun getFruits(): String {
        return fruits
    }

    fun getDeliveryDate(): String {
        return deliveryDate
    }
}