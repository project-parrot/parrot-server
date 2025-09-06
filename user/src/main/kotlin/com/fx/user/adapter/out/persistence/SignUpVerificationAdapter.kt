package com.fx.user.adapter.out.persistence

import com.fx.global.annotation.hexagonal.CacheAdapter
import com.fx.user.application.port.out.persistence.SignUpVerificationPort
import org.springframework.data.redis.core.StringRedisTemplate
import java.util.UUID
import java.util.concurrent.TimeUnit

@CacheAdapter
class SignUpVerificationAdapter(
    private val redisTemplate: StringRedisTemplate
) : SignUpVerificationPort {

    override fun saveVerificationCode(email: String, code: String, minutes: Long) {
        redisTemplate.opsForValue().set("verification:$email", code, minutes, TimeUnit.MINUTES)
    }

    override fun getVerificationCode(email: String): String? {
        return redisTemplate.opsForValue().get("verification:$email")
    }

    override fun deleteVerificationCode(email: String) {
        redisTemplate.delete("verification:$email")
    }


    override fun saveTempEmail(email: String, code: String): String {
        val token = UUID.randomUUID().toString()
        redisTemplate.opsForValue().set("tempEmail:$token", email, 10, TimeUnit.MINUTES)
        return token
    }

    override fun getEmailByToken(token: String): String? {
        return redisTemplate.opsForValue().get("tempEmail:$token")
    }

    override fun deleteTempEmail(token: String) {
        redisTemplate.delete("tempEmail:$token")
    }

}