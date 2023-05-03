package com.example.models.CRUD

import com.example.database.DatabaseFactory.dbQuery
import com.example.database.ReportDAO
import com.example.models.Report
import com.example.models.Reports
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ReportCRUD : ReportDAO {
    override suspend fun selectAllTreasureReports(idTreasure: Int): List<Report> = dbQuery {
        Reports.select { Reports.idTreasure eq idTreasure }
        .map(::resultRowToReport)
    }

    override suspend fun selectTreasureReportByID(idTreasure: Int, idReport: Int): Report? = dbQuery  {
        Reports
            .select {  (Reports.idReport eq idReport) and (Reports.idTreasure eq idTreasure) }
            .map(::resultRowToReport)
            .singleOrNull()
    }

    override suspend fun postReport(reportToAdd: Report): Report? = dbQuery {
        val insertStatement = Reports.insert {
            it[idUser] = reportToAdd.idUser
            it[idTreasure] = reportToAdd.idTreasure
            it[reportInfo] = reportToAdd.reportInfo
            it[reportDate] = reportToAdd.reportDate
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToReport)
    }

    override suspend fun deleteReport(idReport: Int): Boolean = dbQuery {
        Reports.deleteWhere { Reports.idReport eq idReport } > 0
    }

    override suspend fun deleteReportsOfTreasure(idTreasure: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReportsOfUser(idUser: Int): Boolean {
        TODO("Not yet implemented")
    }


}