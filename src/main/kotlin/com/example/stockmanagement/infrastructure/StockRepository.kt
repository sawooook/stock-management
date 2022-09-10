package com.example.stockmanagement.infrastructure

import com.example.stockmanagement.domain.StockEntity
import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository : JpaRepository<StockEntity, Long> {
}