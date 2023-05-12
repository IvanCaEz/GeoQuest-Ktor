package com.example.routes

import com.example.models.CRUD.*
import com.example.models.Games
import com.example.models.Reviews
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

fun Route.treasureRouting() {
    val treasureCrud = TreasureCRUD()
    val favouriteCrud = FavouriteCRUD()
    val reviewCrud = ReviewCRUD()
    val reportCrud = ReportCRUD()
    val gameCrud = GameCRUD()
    val gson = Gson()
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss")

    route("/treasure") {
        get {
            val listOfTreasures = treasureCrud.selectAllTreasures()
            if (listOfTreasures.isNotEmpty()) {
                call.respond(listOfTreasures)
            } else {
                call.respond(listOf<Treasures>())
                call.respondText("No treasures found.", status = HttpStatusCode.NotFound)
            }
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

        get("{treasureID}/picture") {
            var treasureImage: File = File("")
            val treasureID = call.parameters["treasureID"]
            if (treasureID.isNullOrBlank()) return@get call.respondText(
                "Missing treasure id.",
                status = HttpStatusCode.BadRequest
            )

            val treasurePhoto = treasureCrud.selectTreasureByID(treasureID.toInt())!!.image
            treasureImage = File("src/main/kotlin/com/example/treasure_pictures/$treasurePhoto")
            if (treasureImage.exists()) {
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
                val totalPlayed: Int
                val totalFound: Int

                val games = gameCrud.selectAllTreasureGames(treasureID.toInt())
                if (games.isNotEmpty()) {
                    totalPlayed = games.count()
                    totalFound = games.count { it.solved }
                } else {
                    totalPlayed = 0
                    totalFound = 0
                }

                val totalFavourites = favouriteCrud.selectAllFavouritesByTreasureID(treasureID.toInt()).size
                val reviews = reviewCrud.selectAllTreasureReviews(treasureID.toInt())
                val reportQuantity = reportCrud.selectAllTreasureReports(treasureID.toInt()).size
                var diff = 0L
                games.forEach { game ->
                    val startTime = LocalDateTime.parse(game.timeStart, formatter)
                    val endTime = LocalDateTime.parse(game.timeEnd, formatter)
                    diff += Duration.between(startTime, endTime).toMillis()
                }

                val total = if(games.isEmpty()) 1 else games.size

                val hours = TimeUnit.MILLISECONDS.toHours(diff /total)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(diff / total) % 60
                val seconds = TimeUnit.MILLISECONDS.toSeconds(diff / total) % 60

                val averageTime = "$hours:$minutes:$seconds"

                val treasureStats = TreasureStats(
                    treasureID.toInt(), totalPlayed, totalFound,
                    totalFavourites, reviews.size, reportQuantity, averageTime
                )

                call.respond(treasureStats)

            } else call.respondText(
                "Treasure with id $treasureID not found.",
                status = HttpStatusCode.NotFound
            )
        }

        get("{treasureID}/reviews") {
            val treasureID = call.parameters["treasureID"]
            if (treasureID.isNullOrBlank()) return@get call.respondText(
                "Missing treasure id.", status = HttpStatusCode.BadRequest
            )
            val reviews = reviewCrud.selectAllTreasureReviews(treasureID.toInt())
            if (reviews.isNotEmpty()) {
                call.respond(reviews)

            } else call.respondText(
                "Treasure with id $treasureID doesn't have any reviews yet.",
                status = HttpStatusCode.NotFound
            )
        }
        get("{treasureID}/reviews/{reviewID}") {
            val treasureID = call.parameters["treasureID"]
            val reviewID = call.parameters["reviewID"]

            if (treasureID.isNullOrBlank()) return@get call.respondText(
                "Missing user id.", status = HttpStatusCode.BadRequest
            )
            if (reviewID.isNullOrBlank()) return@get call.respondText(
                "Missing review id.", status = HttpStatusCode.BadRequest
            )

            val review = reviewCrud.selectReviewOfTreasure(treasureID.toInt(), reviewID.toInt())
            if (review != null) {
                call.respond(review)

            } else call.respondText(
                "Review on treasure with id $treasureID and review with id $reviewID not found.",
                status = HttpStatusCode.NotFound
            )
        }
        get("{treasureID}/reviews/{reviewID}/picture") {
            val treasureID = call.parameters["treasureID"]
            val reviewID = call.parameters["reviewID"]

            if (treasureID.isNullOrBlank()) return@get call.respondText(
                "Missing user id.", status = HttpStatusCode.BadRequest
            )
            if (reviewID.isNullOrBlank()) return@get call.respondText(
                "Missing review id.", status = HttpStatusCode.BadRequest
            )

            var reviewImage: File = File("")

            val reviewPhoto = reviewCrud.selectReviewOfTreasure(treasureID.toInt(), reviewID.toInt())!!.photo
            println(reviewPhoto)
            reviewImage = File("src/main/kotlin/com/example/treasure_pictures/review_pictures/$reviewPhoto")
            if (reviewImage.exists()) {
                call.respondFile(reviewImage)
            } else {
                call.respondText("No image found.", status = HttpStatusCode.NotFound)
            }
        }


        get("{treasureID}/reports") {
            val treasureID = call.parameters["treasureID"]
            if (treasureID.isNullOrBlank()) return@get call.respondText(
                "Missing treasure id.", status = HttpStatusCode.BadRequest
            )
            val reports = reportCrud.selectAllTreasureReports(treasureID.toInt())
            if (reports.isNotEmpty()) {
                call.respond(reports)

            } else call.respondText(
                "Treasure with id $treasureID hasn't been reported yet.",
                status = HttpStatusCode.NotFound
            )
        }
        get("{treasureID}/reports/{reportID}") {
            val treasureID = call.parameters["treasureID"]
            val reportID = call.parameters["reportID"]

            if (treasureID.isNullOrBlank()) return@get call.respondText(
                "Missing treasure id.", status = HttpStatusCode.BadRequest
            )
            if (reportID.isNullOrBlank()) return@get call.respondText(
                "Missing report id.", status = HttpStatusCode.BadRequest
            )
            val report = reportCrud.selectTreasureReportByID(treasureID.toInt(), reportID.toInt())
            if (report != null) {
                call.respond(report)
            } else call.respondText(
                "Report with id $report on treasure with id $treasureID not found.",
                status = HttpStatusCode.NotFound
            )
        }

        post {
            val treasureData = call.receiveMultipart()
            var treasureToAdd = Treasures(
                0, "", "", "placeholder_map.png", 0.0, 0.0,
                "", "", "", "", 0.0
            )
            treasureData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        treasureToAdd = gson.fromJson(part.value, Treasures::class.java)
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
                treasureID.toInt(), "", "", "", 0.0, 0.0,
                "", "", "", "", 0.0
            )
            treasureData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        treasureToUpdate = gson.fromJson(part.value, Treasures::class.java)
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
            treasureToUpdate.score = treasureCrud.setScore(treasureID.toInt())
            treasureCrud.updateTreasure(treasureToUpdate)

            return@put call.respondText(
                "Treasure with id ${treasureToUpdate.idTreasure} and ${treasureToUpdate.name} has been updated.",
                status = HttpStatusCode.OK
            )
        }

        put("{treasureID}/score") {
            val treasureID = call.parameters["treasureID"]
            if (treasureID.isNullOrBlank()) return@put call.respondText(
                "Missing treasure id.", status = HttpStatusCode.BadRequest
            )
            val treasureToUpdate = call.receive<Treasures>()
            treasureToUpdate.score = treasureCrud.setScore(treasureID.toInt())
            treasureCrud.updateTreasure(treasureToUpdate)
            return@put call.respondText(
                "Score of treasure with id ${treasureToUpdate.idTreasure} and ${treasureToUpdate.name} has been updated.",
                status = HttpStatusCode.OK
            )
        }

        post("{treasureID}/reviews") {
            val treasureID = call.parameters["treasureID"]
            if (treasureID.isNullOrBlank()) return@post call.respondText(
                "Missing user id.", status = HttpStatusCode.BadRequest
            )
            val reviewData = call.receiveMultipart()
            var reviewToAdd = Reviews(
                0, 0, 0, "", 0, "placeholder_review.png"
            )
            reviewData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        reviewToAdd = gson.fromJson(part.value, Reviews::class.java)
                    }

                    is PartData.FileItem -> {
                        try {
                            reviewToAdd.photo = part.originalFileName as String
                            val fileBytes = part.streamProvider().readBytes()
                            File("src/main/kotlin/com/example/treasure_pictures/review_pictures/" + reviewToAdd.photo)
                                .writeBytes(fileBytes)
                        } catch (e: FileNotFoundException) {
                            println("Error ${e.message}")
                        }
                    }

                    else -> {}
                }
            }

            reviewCrud.postReview(reviewToAdd)

            return@post call.respondText(
                "Treasure with id ${reviewToAdd.idTreasure} reviewed correctly.",
                status = HttpStatusCode.Created
            )
        }
        put("{treasureID}/reviews/{reviewID}") {
            val treasureID = call.parameters["treasureID"]
            val reviewID = call.parameters["reviewID"]

            if (treasureID.isNullOrBlank()) return@put call.respondText(
                "Missing user id.", status = HttpStatusCode.BadRequest
            )
            if (reviewID.isNullOrBlank()) return@put call.respondText(
                "Missing review id.", status = HttpStatusCode.BadRequest
            )
            val reviewData = call.receiveMultipart()
            var reviewToUpdate = Reviews(
                0, 0, 0, "", 0, "placeholder_review.png"
            )
            reviewData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        reviewToUpdate = gson.fromJson(part.value, Reviews::class.java)
                    }

                    is PartData.FileItem -> {
                        try {
                            reviewToUpdate.photo = part.originalFileName as String
                            if (reviewToUpdate.photo != reviewCrud.selectReviewOfTreasure(
                                    treasureID.toInt(),
                                    reviewID.toInt()
                                )!!.photo
                            ) {
                                val fileBytes = part.streamProvider().readBytes()
                                File("src/main/kotlin/com/example/treasure_pictures/review_pictures/" + reviewToUpdate.photo)
                                    .writeBytes(fileBytes)
                            }
                        } catch (e: FileNotFoundException) {
                            println("Error ${e.message}")
                        }
                    }

                    else -> {}
                }
            }
            reviewCrud.updateReview(reviewToUpdate)
            val treasure = treasureCrud.selectTreasureByID(reviewToUpdate.idTreasure)
            treasure!!.score = treasureCrud.setScore(reviewToUpdate.idTreasure)
            treasureCrud.updateTreasure(treasure)
            return@put call.respondText(
                "Review with id ${reviewToUpdate.idReview} updated correctly.",
                status = HttpStatusCode.OK
            )
        }

        delete("{treasureID}") {
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

        delete("{treasureID}/reviews/{reviewID}") {
            val treasureID = call.parameters["treasureID"]
            val reviewID = call.parameters["reviewID"]

            if (treasureID.isNullOrBlank()) return@delete call.respondText(
                "Missing treasure id.", status = HttpStatusCode.BadRequest
            )
            if (reviewID.isNullOrBlank()) return@delete call.respondText(
                "Missing review id.", status = HttpStatusCode.BadRequest
            )
            //Eliminamos review y updateamos el score
            reviewCrud.deleteReview(treasureID.toInt(), reviewID.toInt())
            val treasure = treasureCrud.selectTreasureByID(treasureID.toInt())
            treasure!!.score = treasureCrud.setScore(treasureID.toInt())
            treasureCrud.updateTreasure(treasure)
            call.respondText(
                "Review with id $reviewID on treasure with id $treasureID has been deleted.",
                status = HttpStatusCode.OK
            )
        }
        delete("{treasureID}/reports/{reportID}") {
            val treasureID = call.parameters["treasureID"]
            val reportID = call.parameters["reportID"]

            if (treasureID.isNullOrBlank()) return@delete call.respondText(
                "Missing treasure id.", status = HttpStatusCode.BadRequest
            )
            if (reportID.isNullOrBlank()) return@delete call.respondText(
                "Missing report id.", status = HttpStatusCode.BadRequest
            )

            reportCrud.deleteReport(reportID.toInt())
            call.respondText(
                "Report with id $reportID on treasure with id $treasureID has been deleted.",
                status = HttpStatusCode.OK
            )
        }

        post("{treasureID}/games") {
            val treasureID = call.parameters["treasureID"]
            if (treasureID.isNullOrBlank()) return@post call.respondText(
                "Missing treasure id.", status = HttpStatusCode.BadRequest
            )
            val gameToAdd = call.receive<Games>()
            gameCrud.postGame(gameToAdd)
            call.respondText("Game with id ${gameToAdd.idGame} added.")
        }


    }

}