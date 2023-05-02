package com.example.database

import com.example.models.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert

interface TreasureDAO {
    fun resultRowToTreasure(row: ResultRow) = Treasure(
        idTreasure = row[Treasures.idTreasure],
        name = row[Treasures.name],
        description = row[Treasures.description],
        latitude = row[Treasures.latitude],
        longitude = row[Treasures.longitude],
        image = row[Treasures.image],
        location = row[Treasures.location],
        clue = row[Treasures.clue],
        status = row[Treasures.status],
        difficulty = row[Treasures.difficulty],
        score = row[Treasures.score],
    )

    suspend fun selectAllTreasures(): List<Treasure>
    suspend fun selectTreasureByID(idTreasure: Int): Treasure?
    suspend fun addNewTreasure(treasureToAdd: Treasure): Treasure?
    suspend fun updateTreasure(treasureToUpdate: Treasure): Boolean
    suspend fun deleteTreasure(idTreasure: Int): Boolean

}