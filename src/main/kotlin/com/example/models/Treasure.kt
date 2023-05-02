package com.example.models

import com.example.models.Users.autoIncrement
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Treasure(
    val idTreasure: Int,
    var name: String,
    var description: String,
    var latitude: Double,
    var longitude: Double,
    var location: String,
    var clue: String,
    var status: String,
    var difficulty: String,
    var score: Double
)
object Treasure: Table(){
    val idUser = integer("id_user").autoIncrement("id_user_seq")
    val nickName = varchar("user_nick", 1000)
    val email = varchar("user_email", 1000)
    val password = varchar("user_password", 1000)
    val photo = varchar("user_photo", 1000)
    val userLevel = varchar("user_level", 1000)
    val userRole = varchar("user_role", 1000)
    override val primaryKey = PrimaryKey(idUser, name = "users_pkey")
}
