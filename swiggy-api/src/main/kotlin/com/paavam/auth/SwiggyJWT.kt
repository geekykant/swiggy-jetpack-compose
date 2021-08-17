package com.paavam.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm

class SwiggyJWT private constructor(secretKey: String) {
    private val algorithm = Algorithm.HMAC256(secretKey)
    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .build()

    fun sign(mobileNo: String): String = JWT
        .create()
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .withClaim(CLAIM, mobileNo)
        .sign(algorithm)

    companion object {
        lateinit var instance: SwiggyJWT
            private set

        fun initialize(secretKey: String) {
            synchronized(this) {
                if (!this::instance.isInitialized) {
                    instance = SwiggyJWT(secretKey)
                }
            }
        }

        private const val ISSUER = "Swiggy-JWT-Issuer"
        private const val AUDIENCE = "https://api.paavam.com/swiggy"
        const val CLAIM = "mobileNo"
    }
}