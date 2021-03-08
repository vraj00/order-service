package com.vr.orderservice.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
class FruitsOrder(
    @Id
    @Column(name = "fruit_name")
    var fruitName: String,
    @Column(name = "fruit_price", nullable = false)
    var fruitPrice: Double,
    @Column(name = "fruit_qty", nullable = false)
    var fruitQty: Int,
)