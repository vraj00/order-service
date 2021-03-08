package com.vr.orderservice.models

import javax.persistence.*

@Entity
class Discount(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,
    @Column(name = "fruit_name", nullable = false)
    var fruitName: String,
    @Column(name = "discount_price", nullable = false)
    var DiscountPrice: Double,
    @Column(name = "discount_qty", nullable = false)
    var DiscountQty: Int,
)