package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.security.token.TokenConfig
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.util.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*

fun Application.configureSecurity(config: TokenConfig) {

    authentication {
        jwt {
            val realm = "loscolinabosmolan"

            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.secret))
                    .withAudience(config.audience)
                    .withIssuer(config.issuer)
                    .build()
            )
            validate{ credential ->
                if (credential.payload.audience.contains(config.audience)){
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }

    routing {

    }
}
