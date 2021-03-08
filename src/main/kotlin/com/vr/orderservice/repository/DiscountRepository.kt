package com.vr.orderservice.repository

import com.vr.orderservice.models.Discount
import org.springframework.data.repository.CrudRepository

interface DiscountRepository: CrudRepository<Discount, Long> {
}