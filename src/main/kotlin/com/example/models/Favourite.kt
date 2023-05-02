package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Favourite(
    val idUser: Int,
    val idTreasure: Int
)

object Favourites: Table(){
    val idUser = integer("id_user")
    val idTreasure = integer("treasure_id")
}
