package com.example.stockmanagement.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.time.Duration

@Component
class RedisLockRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {

    fun lock(key: Long): Boolean =
        redisTemplate
            .opsForValue()
            .setIfAbsent(generateKey(key), "lock", Duration.ofMillis(3_000))
            ?: throw IllegalStateException()

    fun unlock(key: Long): Boolean =
        redisTemplate
            .delete(generateKey(key))

    private fun generateKey(key: Long): String = key.toString()
}