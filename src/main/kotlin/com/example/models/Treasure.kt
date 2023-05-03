package com.example.models

import com.example.models.Favourites.references
import com.example.models.Users.autoIncrement
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

@Serializable
data class Treasure(
    val idTreasure: Int,
    var name: String,
    var description: String,
    var image: String,
    var latitude: Double,
    var longitude: Double,
    var location: String,
    var clue: String,
    var status: String,
    var difficulty: String,
    var score: Double
)
object Treasures: Table(){
    val idTreasure = integer("id_treasure").autoIncrement("treasure_id_seq")
    val name = varchar("treasure_name", 1000)
    val description = varchar("treasure_description", 1000)
    val latitude = double("treasure_latitude")
    val longitude = double("treasure_longitude")
    val image = varchar("treasure_image",1000)
    val location = varchar("treasure_location", 1000)
    val clue = varchar("treasure_clue", 1000)
    val status = varchar("treasure_status", 1000)
    val difficulty = varchar("treasure_difficulty", 1000)
    val score = double("treasure_score")
    override val primaryKey = PrimaryKey(idTreasure, name = "treasure_pkey")

}
