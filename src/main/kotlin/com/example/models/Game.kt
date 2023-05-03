package com.example.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.booleanLiteral
import org.jetbrains.exposed.sql.`java-time`.date
import java.sql.Date
import java.time.LocalDate

@Serializable
data class Game(
    val idGame: Int,
    val idTreasure: Int,
    val idUser: Int,
    var solved: Boolean,
    @Contextual var timeStart: LocalDate,
    @Contextual  var timeEnd: LocalDate

)
object Games: Table(){
    val idGame = integer("game_id").autoIncrement("game_id_seq")
    val idTreasure = integer("treasure_id").references(Treasures.idTreasure)
    val idUser = integer("id_user").references(Users.idUser)
    val solved = bool("game_solved")
    val timeStart = date("game_time_start")
    val timeEnd = date("game_time_end")

    override val primaryKey = PrimaryKey(idGame, name = "game_pkey")
}