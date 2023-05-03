package com.example.routes

import com.example.models.CRUD.ReportCRUD
import com.example.models.Reports
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.reportRouting(){
    val reportCrud = ReportCRUD()
    route("/reports"){
        post {
            val reportToAdd = call.receive<Reports>()
            reportCrud.postReport(reportToAdd)
            call.respondText("Report with id ${reportToAdd.idReport} made by user with id ${reportToAdd.idUser} added on treasure with id ${reportToAdd.idTreasure}",
                status = HttpStatusCode.OK)
        }
        get {
            val reportList = reportCrud.selectAllReports()
            if (reportList.isNotEmpty()) call.respond(reportList)
            else call.respondText("No reports found", status = HttpStatusCode.OK)
        }
    }

}