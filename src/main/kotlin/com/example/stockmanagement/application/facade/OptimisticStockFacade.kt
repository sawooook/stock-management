package com.example.stockmanagement.application.facade

import com.example.stockmanagement.application.OptimisticStockService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class OptimisticStockFacade(
    private val optimisticStockService: OptimisticStockService
) {

//    @Transactional
    fun decrease(id: Long, quantity: Long) {
        while (true) {
            try {
                optimisticStockService.decrease(id, quantity)

                break
            } catch (e: Exception) {
                Thread.sleep(50)
            }
        }
    }
}