package com.example.stockmanagement.application.facade

import com.example.stockmanagement.application.StockService
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedissonLockStockFacade(
    private val redissonClient: RedissonClient,
    private val stockService: StockService
) {

    fun desc(key: Long, quan: Long) {
        val lock = redissonClient.getLock(key.toString())
        println("========= lock : ${lock.holdCount}")

        try {
            val isAvailable = lock.tryLock(3, TimeUnit.SECONDS)

            println("========= isAvailable : ${isAvailable}")

            if (!isAvailable) {
                println("LOCK 획득 실패")
            } else {
                println("LOCK 획득 성공")
                stockService.decrease(key, quan)
            }

        } catch (e: Exception) {
            throw RuntimeException()
        }  finally {
            lock.unlock()
        }

    }
}