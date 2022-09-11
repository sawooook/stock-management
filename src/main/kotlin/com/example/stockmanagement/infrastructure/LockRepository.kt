package com.example.stockmanagement.infrastructure

import com.example.stockmanagement.domain.StockEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LockRepository : JpaRepository<StockEntity, Long> {
    @Query(value = "select get_lock(:key, 3000)", nativeQuery = true)
    fun getLock(key: String)

    @Query(value = "select release_lock(:key)", nativeQuery = true)
    fun releaseLock(key: String)
}