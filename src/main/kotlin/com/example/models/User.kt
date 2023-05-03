package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.autoIncColumnType

@Serializable
data class User(
    var idUser: Int,
    var nickName: String,
    var email: String,
    var password: String,
    var photo: String,
    var userLevel: String,
    var userRole: String,
    var favs: List<Treasures>
)
object Users: Table(){
    val idUser = integer("id_user").autoIncrement("id_user_seq")
    val nickName = varchar("user_nick", 1000)
    val email = varchar("user_email", 1000)
    val password = varchar("user_password", 1000)
    val photo = varchar("user_photo", 1000)
    val userLevel = varchar("user_level", 1000)
    val userRole = varchar("user_role", 1000)
    override val primaryKey = PrimaryKey(idUser, name = "users_pkey")
}
