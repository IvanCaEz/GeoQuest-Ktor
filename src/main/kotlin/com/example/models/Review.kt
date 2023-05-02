package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Review (
    val idGame: Int,
    var opinion: String,
    var rating: Int,
    var photo: String
)