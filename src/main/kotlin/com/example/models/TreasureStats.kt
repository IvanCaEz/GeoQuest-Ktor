package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class TreasureStats(
    val idTreasure: Int,
    val totalPlayed: Int,
    val totalFound: Int,
    val totalFavourite: Int,
    val totalReviews: Int,
    val reportQuantity: Int,
    val averageTime: Double
)
