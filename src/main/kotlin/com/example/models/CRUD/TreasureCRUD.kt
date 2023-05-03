package com.example.models.CRUD

import com.example.database.DatabaseFactory.dbQuery
import com.example.database.TreasureDAO
import com.example.models.Treasure
import com.example.models.Treasures
import com.example.models.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class TreasureCRUD: TreasureDAO {
    override suspend fun selectAllTreasures(): List<Treasures> = dbQuery {
        Treasure.selectAll().map(::resultRowToTreasure)
    }

    override suspend fun selectTreasureByID(idTreasure: Int): Treasures? = dbQuery {
        Treasure
            .select { Treasure.idTreasure eq idTreasure }
            .map(::resultRowToTreasure)
            .singleOrNull()
    }

    override suspend fun addNewTreasure(treasureToAdd: Treasures): Treasures? = dbQuery {
        val insertStatement = Treasure.insert {
            it[name] = treasureToAdd.name
            it[description] = treasureToAdd.description
            it[image] = treasureToAdd.image
            it[latitude] = treasureToAdd.latitude
            it[longitude] = treasureToAdd.longitude
            it[clue] = treasureToAdd.clue
            it[difficulty] = treasureToAdd.difficulty
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToTreasure)
    }

    override suspend fun updateTreasure(treasureToUpdate: Treasures): Boolean = dbQuery {
        Treasure.update ({ Treasure.idTreasure eq treasureToUpdate.idTreasure } ){
            it[name] = treasureToUpdate.name
            it[description] = treasureToUpdate.description
            it[image] = treasureToUpdate.image
            it[latitude] = treasureToUpdate.latitude
            it[longitude] = treasureToUpdate.longitude
            it[clue] = treasureToUpdate.clue
            it[difficulty] = treasureToUpdate.difficulty
            it[score] = treasureToUpdate.score
            it[status] = treasureToUpdate.status
        } > 0
    }

    override suspend fun deleteTreasure(idTreasure: Int): Boolean = dbQuery {
        Treasure.deleteWhere { Treasure.idTreasure eq idTreasure } > 0
    }

}