package com.example.models

import com.example.Utils.InstantSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.timestamp
import java.time.Instant

@Serializable
data class Games(
    val idGame: Int,
    val idTreasure: Int,
    val idUser: Int,
    var solved: Boolean,
    var timeStart: String,
    var timeEnd: String

)
object Game: Table(){
    val idGame = integer("game_id").autoIncrement("game_id_seq")
    val idTreasure = integer("treasure_id").references(Treasure.idTreasure)
    val idUser = integer("id_user").references(Users.idUser)
    val solved = bool("game_solved")
    val timeStart = varchar("game_time_start", 128)
    val timeEnd = varchar("game_time_end", 128)


    override val primaryKey = PrimaryKey(idGame, name = "game_pkey")
}