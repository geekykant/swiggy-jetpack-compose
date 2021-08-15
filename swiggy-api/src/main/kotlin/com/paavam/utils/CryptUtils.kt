package com.paavam.utils

import io.ktor.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Encrypts password and returns hex string
 */
fun hash(password: String): String {
    val hmac = Mac.getInstance(KeyProvider.ALGORITHM)
    hmac.init(KeyProvider.hmacKey)
    return hex(hmac.doFinal(password.toByteArray(Charsets.UTF_8)))
}

/**
 * SecretKeySpec provider - used for encryption.
 */
object KeyProvider {
    lateinit var hmacKey: SecretKeySpec
        private set

    fun initialize(secret: String) {
        if (!this::hmacKey.isInitialized) {
            hmacKey = SecretKeySpec(secret.toByteArray(), ALGORITHM)
        }
    }

    const val ALGORITHM = "HmacSHA256"
}