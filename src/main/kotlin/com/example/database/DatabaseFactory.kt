package com.example.database

import com.example.models.CRUD.TreasureCRUD
import com.example.models.CRUD.UserCRUD
import com.example.models.User
import com.example.models.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.name
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.postgresql.ds.PGSimpleDataSource

object DatabaseFactory {
    fun init() {
        val userCRUD = UserCRUD()
        val driverClassName = "org.h2.Driver"
        val username = "avnadmin"
        val password = "AVNS_hm2mNlomL9mn9cePWwo"
        val jdbcURL = "jdbc:postgresql://geoquest-database-geoquest-1.aivencloud.com:26293/geoquestdb?sslmode=require"
        val database = Database.connect(jdbcURL, driverClassName, username, password)


        transaction(database) {

            runBlocking {

                val userToUpdate = User(1, "Alejandro", "ale@jandro.com", "123456", "placeholder.png", "Noob", "Admin", listOf())
               println( userCRUD.addNewUser("Alejandro","ale@jandro.com", "123456" ))
            }
        }




    }
    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}