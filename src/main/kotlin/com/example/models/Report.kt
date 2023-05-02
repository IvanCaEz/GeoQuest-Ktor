package com.example.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Report(
    val idReport: Int,
    val userId: Int,
    val treasureId: Int,
    val reportInfo: String,
    @Contextual val reportDate: Date
)