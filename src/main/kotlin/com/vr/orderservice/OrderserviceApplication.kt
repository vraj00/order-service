package com.vr.orderservice

import com.vr.orderservice.models.Discount
import com.vr.orderservice.models.FruitsOrder
import com.vr.orderservice.repository.DiscountRepository
import com.vr.orderservice.repository.FruitsOrderRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OrderserviceApplication(private val repo: FruitsOrderRepository,
                              private val discountRepo : DiscountRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {
        loadData(repo, discountRepo)
    }
}

fun main(args: Array<String>) {
    runApplication<OrderserviceApplication>(*args)
}

fun loadData(repo : FruitsOrderRepository, discountRepo : DiscountRepository) {
    try {
        repo.save(FruitsOrder("APPLE", 0.60, 10))
        repo.save(FruitsOrder("ORANGE", 0.25, 9))

        discountRepo.save(Discount(0,"APPLE", 0.60, 2))
        discountRepo.save(Discount(0,"ORANGE", 0.50, 3))
    } catch (exception: Exception) {
        println("Error loading data!")
    }
}
