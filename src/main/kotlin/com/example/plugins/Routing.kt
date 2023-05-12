package com.example.plugins

import com.example.routes.reportRouting
import com.example.routes.treasureRouting
import com.example.routes.userRouting
import com.example.security.hashing.HashingService
import com.example.security.token.TokenClaim
import com.example.security.token.TokenConfig
import com.example.security.token.TokenService
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.resources.*
import io.ktor.resources.*
import io.ktor.server.resources.Resources
import kotlinx.serialization.Serializable
import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*

fun Application.configureRouting(hashingService: HashingService, tokenService: TokenService, tokenConfig: TokenConfig) {
    install(Resources)
    routing {

        userRouting(hashingService, tokenService, tokenConfig)
        treasureRouting()
        reportRouting()
    }
    routing {
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
        openAPI(path="openapi", swaggerFile = "openapi/documentation.yaml")
    }
}
