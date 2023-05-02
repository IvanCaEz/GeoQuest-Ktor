package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class UserStats(
    var idUser: Int,
    var solved: Int,
    var notSolved: Int,
    var reportQuantity: Int,
    var averageTime: Double
)