package com.example.routes

import com.example.models.CRUD.TreasureCRUD
import io.ktor.server.routing.*

fun Route.treasureRouting(){
    val treasureCrud = TreasureCRUD()

}