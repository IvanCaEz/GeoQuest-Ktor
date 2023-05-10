package com.example.database

import com.example.models.*
import com.example.models.CRUD.TreasureCRUD
import com.example.models.CRUD.UserCRUD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.name
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.postgresql.ds.PGSimpleDataSource
import java.sql.Timestamp
import java.time.Duration

object DatabaseFactory {
    fun init() {
        val driverClassName = "org.h2.Driver"
        val username = "avnadmin"
        val password = "AVNS_hm2mNlomL9mn9cePWwo"
        val jdbcURL = "jdbc:postgresql://geoquest-database-geoquest-1.aivencloud.com:26293/geoquestdb?sslmode=require"
        val database = Database.connect(jdbcURL, driverClassName, username, password)


        transaction(database) {
            //SchemaUtils.create(Users, Treasure, Review, Favourite, Report,Game)

        }




    }
    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}