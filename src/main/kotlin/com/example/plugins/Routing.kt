package com.example.plugins

import com.example.routes.reportRouting
import com.example.routes.treasureRouting
import com.example.routes.userRouting
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.resources.*
import io.ktor.resources.*
import io.ktor.server.resources.Resources
import kotlinx.serialization.Serializable
import io.ktor.server.application.*

fun Application.configureRouting() {
    install(Resources)
    routing {
        userRouting()
        treasureRouting()
        reportRouting()
    }
}
