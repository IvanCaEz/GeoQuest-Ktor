package com.example

import com.example.database.DatabaseFactory
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.security.hashing.SHA256HashingService
import com.example.security.token.JwtTokenService
import com.example.security.token.TokenConfig
import io.ktor.http.*
import io.ktor.server.plugins.cors.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()
    val tokenService = JwtTokenService()

    val secret = "colinabo"
    val issuer = "http://0.0.0.0:8080"
    val audience = "user"
    val myRealm = "loscolinabosmolan"

    val tokenConfig = TokenConfig(
        issuer = issuer,
        audience = audience,
        expiresIn = 365L + 1000L * 60L * 60L * 24L,
        secret = secret
    )
    val hashingService = SHA256HashingService()

    configureSecurity(tokenConfig)
    configureSerialization()
    configureRouting(hashingService, tokenService, tokenConfig)
    install(CORS) {
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("GeoQuest")
        allowHeader("Content-Type")
        allowHeader("Authorization")
        allowHeader("Access-Control-Allow-Origin")
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }
}
