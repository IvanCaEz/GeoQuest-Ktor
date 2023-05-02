package com.example.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.sql.Date

@Serializable
data class Game(
    val idGame: Int,
    val idTreasure: Int,
    val idUser: Int,
    var solved: Boolean,
    @Contextual var timeStart: Date,
    @Contextual  var timeEnd: Date,
    var review: Review

)