package com.example.models.CRUD

import com.example.database.DatabaseFactory.dbQuery
import com.example.database.ReportDAO
import com.example.models.Reports
import com.example.models.Report
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ReportCRUD : ReportDAO {
    override suspend fun selectAllTreasureReports(idTreasure: Int): List<Reports> = dbQuery {
        Report.select { Report.idTreasure eq idTreasure }
        .map(::resultRowToReport)
    }

    override suspend fun selectTreasureReportByID(idTreasure: Int, idReport: Int): Reports? = dbQuery  {
        Report
            .select {  (Report.idReport eq idReport) and (Report.idTreasure eq idTreasure) }
            .map(::resultRowToReport)
            .singleOrNull()
    }

    override suspend fun selectAllReportByUserId(userId: Int): List<Reports> = dbQuery {
        Report.select { Report.idUser eq userId }
            .map(::resultRowToReport)
    }

    override suspend fun postReport(reportsToAdd: Reports): Reports? = dbQuery {
        val insertStatement = Report.insert {
            it[idUser] = reportsToAdd.idUser
            it[idTreasure] = reportsToAdd.idTreasure
            it[reportInfo] = reportsToAdd.reportInfo
            it[reportDate] = reportsToAdd.reportDate
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToReport)
    }

    override suspend fun deleteReport(idReport: Int): Boolean = dbQuery {
        Report.deleteWhere { Report.idReport eq idReport } > 0
    }

    override suspend fun deleteReportsOfTreasure(idTreasure: Int): Boolean = dbQuery {
        Report.deleteWhere { Report.idTreasure eq idTreasure } > 0
    }

    override suspend fun deleteReportsOfUser(idUser: Int): Boolean = dbQuery {
        Report.deleteWhere { Report.idUser eq idUser } > 0
    }


}