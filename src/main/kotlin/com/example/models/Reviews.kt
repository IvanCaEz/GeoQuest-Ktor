package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Reviews (
    val idReview: Int,
    val idTreasure: Int,
    val idUser: Int,
    var opinion: String,
    var rating: Int,
    var photo: String
)
object Review: Table(){
    val idReview = integer("id_review").autoIncrement("id_review_seq")
    val idTreasure = integer("id_treasure").references(Treasure.idTreasure)
    val idUser = integer("id_user").references(Users.idUser)
    val opinion = varchar("review_opinion", 1000)
    val rating = integer("review_rating")
    val photo = varchar("review_photo", 1000)
    override val primaryKey = PrimaryKey(idReview, name = "review_pkey")
}