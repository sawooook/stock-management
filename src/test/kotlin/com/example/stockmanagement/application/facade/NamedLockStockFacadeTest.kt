package com.example.stockmanagement.application.facade

import com.example.stockmanagement.domain.StockEntity
import com.example.stockmanagement.infrastructure.StockRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
class NamedLockStockFacadeTest {

    @Autowired
    lateinit var namedLockStockFacade: NamedLockStockFacade

    @Autowired
    lateinit var stockRepository: StockRepository

    @BeforeEach
    fun makeStock() {
        stockRepository.save(
            StockEntity(1L, 100, 100)
        )
    }

    @Test
    fun `동시에_100개의_요청`() {
        val threadCount = 100
        val threadPool = Executors.newFixedThreadPool(32)
        val countDownLatch = CountDownLatch(threadCount)


        for (i in 0..threadCount) {
            threadPool.submit {
                runCatching {
                    namedLockStockFacade.dec(1L, 1L)
                }.also {
                    countDownLatch.countDown()
                }
            }
        }

        countDownLatch.await()

        val stock = stockRepository.findByIdOrNull(1L) ?: throw IllegalArgumentException()
        assertEquals(0L, stock.quantity)
    }

}
