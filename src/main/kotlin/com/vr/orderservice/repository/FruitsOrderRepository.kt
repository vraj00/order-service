package com.vr.orderservice.repository

import com.vr.orderservice.models.FruitsOrder
import org.springframework.data.repository.CrudRepository

interface FruitsOrderRepository: CrudRepository<FruitsOrder, String> {
    fun findByFruitName(id: String): FruitsOrder
}