package com.example.stockmanagement.application.facade

import com.example.stockmanagement.application.StockService
import com.example.stockmanagement.infrastructure.RedisLockRepository
import org.springframework.stereotype.Service

@Service
class LettuceLockStockFacade(
    private val redisLockRepository: RedisLockRepository,
    private val stockService: StockService
) {

    fun dec(key: Long, quan: Long) {
        while (!redisLockRepository.lock(key)) {
            println("==================== get")
            Thread.sleep(100)
        }

        try {
            stockService.decrease(key, quan)
        } finally {
            redisLockRepository.unlock(key)
        }
    }
}