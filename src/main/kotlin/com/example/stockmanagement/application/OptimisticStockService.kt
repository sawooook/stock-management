package com.example.stockmanagement.application

import com.example.stockmanagement.infrastructure.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OptimisticStockService(
    private val stockRepository: StockRepository
) {

//    @Transactional
    fun decrease(id: Long, quantity: Long) {
        val stock = stockRepository.findByIdWithOptimisticLock(id)
        stock.decrease(quantity)

        stockRepository.save(stock)
    }
}