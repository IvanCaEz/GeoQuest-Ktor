package com.example.database

import com.example.models.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import java.util.*

interface ReportDAO {
    fun resultRowToReport(row: ResultRow) = Report(
        idReport = row[Reports.idReport],
        idTreasure = row[Reports.idTreasure],
        idUser = row[Reports.idUser],
        reportInfo = row[Reports.reportInfo],
        reportDate = row[Reports.reportDate],
        )

    suspend fun selectAllTreasureReports(idTreasure: Int): List<Report>
    suspend fun selectTreasureReportByID(idTreasure: Int, idReport: Int): Report?
    suspend fun selectAllReportByUserId(userId: Int): List<Report>
    suspend fun postReport(reportToAdd: Report): Report?
    suspend fun deleteReport(idReport: Int): Boolean
    suspend fun deleteReportsOfTreasure(idTreasure: Int): Boolean
    suspend fun deleteReportsOfUser(idUser: Int): Boolean


}