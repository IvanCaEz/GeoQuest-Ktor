package com.example.plugins

import io.ktor.server.auth.*
import io.ktor.util.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSecurity() {

    authentication {
        val myRealm = "MyRealm"
        val usersInMyRealmToHA1: Map<String, ByteArray> = mapOf(
            // pass="test", HA1=MD5("test:MyRealm:pass")="fb12475e62dedc5c2744d98eb73b8877"
            "test" to hex("fb12475e62dedc5c2744d98eb73b8877")
        )

        digest("myDigestAuth") {
            digestProvider { userName, realm ->
                usersInMyRealmToHA1[userName]
            }
        }
    }
    routing {

    }
}
