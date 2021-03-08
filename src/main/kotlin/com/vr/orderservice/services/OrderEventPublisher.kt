package com.vr.orderservice.services

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class OrderEventPublisher(val applicationEventPublisher: ApplicationEventPublisher) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun publisher(message : String, fruits: String, deliveryDate: String) {
        logger.info("Order Event Publisher: $message")
        val event = OrderCompleteEvent(this, message, fruits, deliveryDate)
        applicationEventPublisher.publishEvent(event)
    }
}