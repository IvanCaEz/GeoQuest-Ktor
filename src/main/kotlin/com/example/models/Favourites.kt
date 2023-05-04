package com.example.models

import com.example.models.Game.references
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Favourites(
    val idUser: Int,
    val idTreasure: Int
)

object Favourite: Table(){
    val idUser = integer("id_user").references(Users.idUser)
    val idTreasure = integer("treasure_id").references(Treasure.idTreasure)
}
