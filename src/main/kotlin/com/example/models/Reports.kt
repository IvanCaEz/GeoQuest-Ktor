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
    val reportDate: String
)

object Report: Table(){
    val idReport = integer("report_id").autoIncrement("report_id_seq")
    val idUser = integer("id_user").references(Users.idUser)
    val idTreasure = integer("treasure_id").references(Treasure.idTreasure)
    val reportInfo = varchar("report_info", 500)
    val reportDate = varchar("report_date", 100)
    override val primaryKey = PrimaryKey(idReport, name = "report_pkey")
}