package com.example.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.date
import java.time.LocalDate

@Serializable
data class Reports(
    val idReport: Int,
    val idUser: Int,
    val idTreasure: Int,
    val reportInfo: String,
    @Contextual val reportDate: LocalDate
)

object Report: Table(){
    val idReport = integer("report_id").autoIncrement("report_id_seq")
    val idUser = integer("id_user")
    val idTreasure = integer("treasure_id")
    val reportInfo = varchar("report_info", 1000)
    val reportDate = date("report_date")
    override val primaryKey = PrimaryKey(idReport, name = "report_pkey")
}