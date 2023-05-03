package com.example.database

import com.example.models.*
import org.jetbrains.exposed.sql.ResultRow

interface ReportDAO {
    fun resultRowToReport(row: ResultRow) = Reports(
        idReport = row[Report.idReport],
        idTreasure = row[Report.idTreasure],
        idUser = row[Report.idUser],
        reportInfo = row[Report.reportInfo],
        reportDate = row[Report.reportDate],
        )

    suspend fun selectAllTreasureReports(idTreasure: Int): List<Reports>
    suspend fun selectTreasureReportByID(idTreasure: Int, idReport: Int): Reports?
    suspend fun selectAllReportByUserId(userId: Int): List<Reports>
    suspend fun postReport(reportsToAdd: Reports): Reports?
    suspend fun deleteReport(idReport: Int): Boolean
    suspend fun deleteReportsOfTreasure(idTreasure: Int): Boolean
    suspend fun deleteReportsOfUser(idUser: Int): Boolean


}