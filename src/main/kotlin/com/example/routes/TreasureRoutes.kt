package com.example.routes

import com.example.models.CRUD.*
import com.example.models.TreasureStats
import com.example.models.Treasures
import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException
import java.time.Duration
import java.time.LocalTime
import java.util.concurrent.TimeUnit

fun Route.treasureRouting() {
    val treasureCrud = TreasureCRUD()
    val favouriteCrud = FavouriteCRUD()
    val reviewCrud = ReviewCRUD()
    val reportCrud = ReportCRUD()
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

        get("{treasureID}/picture"){
            var treasureImage: File = File("")
            val treasureID = call.parameters["treasureID"]
            if (treasureID.isNullOrBlank()) return@get call.respondText("Missing treasure id.",
                status = HttpStatusCode.BadRequest)

            val treasurePhoto = treasureCrud.selectTreasureByID(treasureID.toInt())!!.image
            treasureImage = File("src/main/kotlin/com/example/treasure_pictures/$treasurePhoto")
            if (treasureImage.exists()){
                call.respondFile(treasureImage)
            } else {
                call.respondText("No image found.", status = HttpStatusCode.NotFound)
            }
        }

        get("{treasureID}/stats") {
            val treasureID = call.parameters["treasureID"]
            if (treasureID.isNullOrBlank()) return@get call.respondText(
                "Missing treasure id.", status = HttpStatusCode.BadRequest
            )
            val treasure = treasureCrud.selectTreasureByID(treasureID.toInt())
            if (treasure != null) {
                val games = gameCrud.selectAllTreasureGames(treasureID.toInt())

                val totalPlayed = games.count()
                val totalFound = games.count { it.solved }
                val totalFavourites = favouriteCrud.selectAllFavouritesByTreasureID(treasureID.toInt()).size
                val reviews = reviewCrud.selectAllTreasureReviews(treasureID.toInt())
                val reportQuantity = reportCrud.selectAllTreasureReports(treasureID.toInt()).size
                var diff = 0L
                games.forEach { game ->
                    diff += Duration.between(game.timeStart, game.timeEnd).toMillis()
                }

                val hours = TimeUnit.MILLISECONDS.toHours(diff/games.size)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(diff/games.size) % 60
                val seconds = TimeUnit.MILLISECONDS.toSeconds(diff/games.size) % 60
                val averageTime = "$hours:$minutes:$seconds"

               val treasureStats = TreasureStats(treasureID.toInt(), totalPlayed, totalFound,
                   totalFavourites, reviews.size, reportQuantity, averageTime)

                call.respond(treasureStats)

            } else call.respondText(
                "Treasure with id $treasureID not found.",
                status = HttpStatusCode.NotFound
            )
        }

        post {
            val treasureData = call.receiveMultipart()
            var treasureToAdd = Treasures(
                0, "", "", "", 0.0, 0.0,
                "", "", "", "", 0.0)
            val gson = Gson()
            treasureData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        treasureToAdd = Json.decodeFromString(part.value)
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
            var treasureToUpdate = Treasures(
                0, "", "", "", 0.0, 0.0,
                "", "", "", "", 0.0)
            val gson = Gson()
            treasureData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        treasureToUpdate = gson.fromJson(part.value, Treasures::class.java)
                    }
                    is PartData.FileItem -> {
                        try {
                            treasureToUpdate.image = part.originalFileName as String
                            if (treasureToUpdate.image != treasureCrud.selectTreasureByID(treasureID.toInt())!!.image){
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

            val reviews = reviewCrud.selectAllTreasureReviews(treasureID.toInt())
            treasureToUpdate.score = reviews.sumOf { review -> review.rating.toDouble() } / reviews.size
            treasureCrud.updateTreasure(treasureToUpdate)
            return@put call.respondText(
                "Treasure with id ${treasureToUpdate.idTreasure} and ${treasureToUpdate.name} has been updated.",
                status = HttpStatusCode.Created
            )
        }
        delete("{treasureID}"){
            val treasureID = call.parameters["treasureID"]
            if (treasureID.isNullOrBlank()) return@delete call.respondText(
                "Missing treasure id.", status = HttpStatusCode.BadRequest
            )
            //Eliminamos, game, reviews y reports
            gameCrud.deleteTreasureGame(treasureID.toInt())
            reviewCrud.deleteReviewsOfTreasure(treasureID.toInt())
            reportCrud.deleteReportsOfTreasure(treasureID.toInt())
            treasureCrud.deleteTreasure(treasureID.toInt())
            call.respondText(
                "Treasure with id $treasureID has been deleted.",
                status = HttpStatusCode.OK
            )
        }
    }

}