package com.vr.orderservice.services

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class OrderEventListener : ApplicationListener<OrderCompleteEvent> {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun onApplicationEvent(event: OrderCompleteEvent) {
        if (!event.getMessage()!!.contains("Failure")) {
            logger.info("Received new order event : ${event.getMessage()} " +
                    ": for : ${event.getFruits()} : Estimated Delivery: ${event.getDeliveryDate()}")
        } else{
            logger.info("Received new order event : ${event.getMessage()} " +
                    ": for : ${event.getFruits()}")
        }

    }

}