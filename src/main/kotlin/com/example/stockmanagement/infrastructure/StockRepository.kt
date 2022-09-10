package com.example.stockmanagement.infrastructure

import com.example.stockmanagement.domain.StockEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import javax.persistence.LockModeType

interface StockRepository : JpaRepository<StockEntity, Long> {
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from StockEntity s where s.id = :id")
    fun findByIdWithPessimisticLock(id: Long): StockEntity

    @Lock(value = LockModeType.OPTIMISTIC)
    @Query("select s from StockEntity s where s.id = :id")
    fun findByIdWithOptimisticLock(id: Long): StockEntity
}