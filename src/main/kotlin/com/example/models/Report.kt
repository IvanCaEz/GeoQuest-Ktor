package com.example.models

import com.example.models.Users.autoIncrement
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.CurrentDateTime
import org.jetbrains.exposed.sql.`java-time`.date
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDate
import java.util.Date

@Serializable
data class Report(
    val idReport: Int,
    val idUser: Int,
    val idTreasure: Int,
    val reportInfo: String,
    @Contextual val reportDate: LocalDate
)

object Reports: Table(){
    val idReport = integer("report_id").autoIncrement("report_id_seq")
    val idUser = integer("id_user")
    val idTreasure = integer("treasure_id")
    val reportInfo = varchar("report_info", 1000)
    val reportDate = date("report_date")
    override val primaryKey = PrimaryKey(idReport, name = "report_pkey")
}