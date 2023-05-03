package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Favourites(
    val idUser: Int,
    val idTreasure: Int
)

object Favourite: Table(){
    val idUser = integer("id_user")
    val idTreasure = integer("treasure_id")
}
