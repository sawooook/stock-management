package com.example.stockmanagement.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Version

@Entity
class StockEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    var productId: Long,

    var quantity: Long,

) {
    fun decrease(quantity: Long) {
        if (this.quantity - quantity < 0) {
            throw java.lang.IllegalArgumentException("수량을 뺄 수 없습니다")
        }

        this.quantity = this.quantity - quantity
    }
}