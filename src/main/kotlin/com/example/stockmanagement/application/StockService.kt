package com.example.stockmanagement.application

import com.example.stockmanagement.infrastructure.StockRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class StockService(
    private val stockRepository: StockRepository
) {

    /**
     * Synchronized
     * Transactional이 달려 있을 경우 재고처리 로직이 맞지 않았음
     * 트랜잭션이 종료 될때 영속성 컨텍스트가 닫히면서, flush를 함
     * flush전에 요청이 들어와 select를 하면 update 전이기때문에 재고가 안맞음
     * */

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Synchronized
    fun decrease(id: Long, quantity: Long) {
        val stock = stockRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("제고가 존재하지 않습니다")
        stock.decrease(quantity)
        stockRepository.saveAndFlush(stock)
    }

}