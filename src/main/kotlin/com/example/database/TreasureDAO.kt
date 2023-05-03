package com.example.database

import com.example.models.Treasure
import com.example.models.Treasures
import com.example.models.User
import com.example.models.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert

interface TreasureDAO {
    fun resultRowToTreasure(row: ResultRow) = Treasures(
        idTreasure = row[Treasure.idTreasure],
        name = row[Treasure.name],
        description = row[Treasure.description],
        latitude = row[Treasure.latitude],
        longitude = row[Treasure.longitude],
        image = row[Treasure.image],
        location = row[Treasure.location],
        clue = row[Treasure.clue],
        status = row[Treasure.status],
        difficulty = row[Treasure.difficulty],
        score = row[Treasure.score],
    )

    suspend fun selectAllTreasures(): List<Treasures>
    suspend fun selectTreasureByID(idTreasure: Int): Treasures?
    suspend fun selectAllTreasuresSolvedByUser(idUser: Int): List<Treasures>

    suspend fun addNewTreasure(treasureToAdd: Treasures): Treasures?
    suspend fun updateTreasure(treasureToUpdate: Treasures): Boolean
    suspend fun deleteTreasure(idTreasure: Int): Boolean

    suspend fun setScore(idTreasure: Int): Double
}