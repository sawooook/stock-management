package com.example.stockmanagement.application.facade

import com.example.stockmanagement.application.StockService
import com.example.stockmanagement.infrastructure.LockRepository
import com.example.stockmanagement.infrastructure.StockRepository
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
class NamedLockStockFacade(
    private val lockRepository: LockRepository,
    private val stockService: StockService
) {

    @Transactional
    fun dec(id: Long, quan: Long) {
        try {
            lockRepository.getLock(id.toString())
            println("======= GET")
            stockService.decrease(id, quan)
        } finally {
            println("=--------")
            lockRepository.releaseLock(id.toString())

            println("======= RELEASE")
        }
    }

}