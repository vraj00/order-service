package com.vr.orderservice

import com.vr.orderservice.models.FruitsOrder
import com.vr.orderservice.repository.FruitsOrderRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OrderserviceApplication(private val repo: FruitsOrderRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {
        loadData(repo)
    }
}

fun main(args: Array<String>) {
    runApplication<OrderserviceApplication>(*args)
}

fun loadData(repo : FruitsOrderRepository) {
    try {
        repo.save(FruitsOrder("APPLE", 0.60, 10))
        repo.save(FruitsOrder("ORANGE", 0.25, 9))
    } catch (exception: Exception) {
        println("Error loading data!")
    }
}
