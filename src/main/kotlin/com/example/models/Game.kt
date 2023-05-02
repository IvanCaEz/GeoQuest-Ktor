package com.example.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.booleanLiteral
import org.jetbrains.exposed.sql.`java-time`.date
import java.sql.Date

@Serializable
data class Game(
    val idGame: Int,
    val idTreasure: Int,
    val idUser: Int,
    var solved: Boolean,
    @Contextual var timeStart: Date,
    @Contextual  var timeEnd: Date,
    var review: Review
)
object Games: Table(){
    val idGame = integer("game_id").autoIncrement("game_id_seq")
    val idTreasure = integer("treasure_id")
    val idUser = integer("id_user")
    val solved = bool("game_solved")
    val timeStart = date("game_time_start")
    val timeEnd = date("game_time_end")

    // Esto y el idGame son componentes de la review
    val reviewImage = varchar("game_image", 1000)
    val gameScore = integer("game_rating")
    val gameReview = varchar("game_review", 1000)

    override val primaryKey = PrimaryKey(idGame, name = "game_pkey")
}