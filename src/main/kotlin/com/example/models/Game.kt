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
    @Contextual @Serializable(with=InstantSerializer::class) var timeStart: Instant,
    @Contextual @Serializable(with=InstantSerializer::class) var timeEnd: Instant

)
object Game: Table(){
    val idGame = integer("game_id").autoIncrement("game_id_seq")
    val idTreasure = integer("treasure_id").references(Treasure.idTreasure)
    val idUser = integer("id_user").references(Users.idUser)
    val solved = bool("game_solved")
    val timeStart = timestamp("game_time_start")
    val timeEnd = timestamp("game_time_end")


    override val primaryKey = PrimaryKey(idGame, name = "game_pkey")
}