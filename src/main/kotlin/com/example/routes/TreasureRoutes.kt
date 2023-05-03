package com.example.routes

import com.example.models.CRUD.FavouriteCRUD
import com.example.models.CRUD.GameCRUD
import com.example.models.CRUD.TreasureCRUD
import com.example.models.Treasure
import com.example.models.TreasureStats
import com.example.models.User
import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.io.FileNotFoundException

fun Route.treasureRouting() {
    val treasureCrud = TreasureCRUD()
    val favouriteCrud = FavouriteCRUD()
    val gameCrud = GameCRUD()
    route("/treasure") {
        get {
            val listOfTreasures = treasureCrud.selectAllTreasures()
            if (listOfTreasures.isNotEmpty()) {
                call.respond(listOfTreasures)
            } else call.respondText("No treasures found.", status = HttpStatusCode.OK)
        }
        get("{treasureID}") {
            val treasureID = call.parameters["treasureID"]
            if (treasureID.isNullOrBlank()) return@get call.respondText(
                "Missing treasure id.", status = HttpStatusCode.BadRequest
            )
            val treasure = treasureCrud.selectTreasureByID(treasureID.toInt())
            if (treasure != null) {
                call.respond(treasure)
            } else call.respondText(
                "Treasure with id $treasureID not found.",
                status = HttpStatusCode.NotFound
            )
        }

        get("{treasureID}/stats") {
            val treasureID = call.parameters["treasureID"]
            if (treasureID.isNullOrBlank()) return@get call.respondText(
                "Missing treasure id.", status = HttpStatusCode.BadRequest
            )
            val treasure = treasureCrud.selectTreasureByID(treasureID.toInt())
            if (treasure != null) {

                //TODO
                //Buscar en games, reports, reviews y favs por idTreasure y construir TreasureStats()

                val games = gameCrud.selectAllTreasureGames(treasureID.toInt())
                val gamesPlayed = games.count()
                val gamesSolved = games.count { it.solved }
                val gamesNotSolved = games.count { !it.solved }

                val totalFavourites = favouriteCrud.selectAllFavouritesByTreasureID(treasureID.toInt()).size

                //TreasureStats(treasureID.toInt(), gamesPlayed, gamesSolved, gamesNotSolved)

            } else call.respondText(
                "Treasure with id $treasureID not found.",
                status = HttpStatusCode.NotFound
            )
        }

        post {
            val treasureData = call.receiveMultipart()
            var treasureToAdd = Treasure(
                0, "", "", "", 0.0, 0.0,
                "", "", "", "", 0.0
            )
            val gson = Gson()
            treasureData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        treasureToAdd = gson.fromJson(part.value, Treasure::class.java)
                    }

                    is PartData.FileItem -> {
                        try {
                            treasureToAdd.image = part.originalFileName as String
                            val fileBytes = part.streamProvider().readBytes()
                            File("src/main/kotlin/com/example/treasure_pictures/" + treasureToAdd.image)
                                .writeBytes(fileBytes)
                        } catch (e: FileNotFoundException) {
                            println("Error ${e.message}")
                        }
                    }

                    else -> {}
                }
                println("Subido ${part.name}")
            }
            treasureCrud.addNewTreasure(treasureToAdd)
            return@post call.respondText(
                "Treasure with id ${treasureToAdd.idTreasure} and ${treasureToAdd.name} stored correctly",
                status = HttpStatusCode.Created
            )
        }

        put("{treasureID}") {
            val treasureID = call.parameters["treasureID"]
            if (treasureID.isNullOrBlank()) return@put call.respondText(
                "Missing treasure id.", status = HttpStatusCode.BadRequest
            )
            val treasureData = call.receiveMultipart()
            var treasureToUpdate = Treasure(
                0, "", "", "", 0.0, 0.0,
                "", "", "", "", 0.0
            )
            val gson = Gson()
            treasureData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        treasureToUpdate = gson.fromJson(part.value, Treasure::class.java)
                    }

                    is PartData.FileItem -> {
                        try {
                            treasureToUpdate.image = part.originalFileName as String
                            if (treasureToUpdate.image != treasureCrud.selectTreasureByID(treasureID.toInt())!!.image) {
                                val fileBytes = part.streamProvider().readBytes()
                                File("src/main/kotlin/com/example/treasure_pictures/" + treasureToUpdate.image)
                                    .writeBytes(fileBytes)
                            }
                        } catch (e: FileNotFoundException) {
                            println("Error ${e.message}")
                        }
                    }

                    else -> {}
                }
                println("Subido ${part.name}")
            }
            //TODO() update score
            treasureCrud.updateTreasure(treasureToUpdate)
            return@put call.respondText(
                "Treasure with id ${treasureToUpdate.idTreasure} and ${treasureToUpdate.name} has been updated.",
                status = HttpStatusCode.Created
            )
        }
        delete("{treasureID}") {
            val treasureID = call.parameters["treasureID"]
            if (treasureID.isNullOrBlank()) return@delete call.respondText(
                "Missing treasure id.", status = HttpStatusCode.BadRequest
            )
            //TODO() Eliminar, reviews y reports
            gameCrud.deleteTreasureGame(treasureID.toInt())
            treasureCrud.deleteTreasure(treasureID.toInt())
            call.respondText(
                "Treasure with id $treasureID has been deleted.",
                status = HttpStatusCode.OK
            )

        }
    }

}