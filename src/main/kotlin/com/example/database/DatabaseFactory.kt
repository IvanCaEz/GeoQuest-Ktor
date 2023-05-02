package com.example.database

import com.example.models.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.postgresql.ds.PGSimpleDataSource

object DatabaseFactory {
    fun init() {
        /*
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:postgresql://geoquest-database-geoquest-1.aivencloud.com:26293/"
        val database = Database.connect(jdbcURL, driverClassName)
         */
        val dataSource = PGSimpleDataSource().apply {
            user = "avnadmin"
            password = "AVNS_hm2mNlomL9mn9cePWwo"
            databaseName = "geoquestdb"
        }
        Database.connect(dataSource)
        transaction {
            SchemaUtils.createMissingTablesAndColumns(Users)
        }
    }
}