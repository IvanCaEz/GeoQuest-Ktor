package com.example.models.CRUD

import com.example.database.DatabaseFactory.dbQuery
import com.example.database.GameDAO
import com.example.models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.selectAll

class GameCRUD: GameDAO {
    override suspend fun selectAllGames(): List<Games>  = dbQuery {
        Game.selectAll().map(::resultRowToGame)
    }

    override suspend fun selectGameByGameID(idGame: Int): Games? = dbQuery {
        Game
            .select { Game.idGame eq idGame }
            .map(::resultRowToGame)
            .singleOrNull()
    }

    override suspend fun selectAllUserGames(idUser: Int): List<Games> = dbQuery {
        Game
            .select { Game.idUser eq idUser }
            .map(::resultRowToGame)
    }

    override suspend fun selectAllTreasureGames(idTreasure: Int): List<Games> = dbQuery {
        Game
            .select { Game.idTreasure eq idTreasure }
            .map(::resultRowToGame)
    }




    override suspend fun postGame(gamesToAdd: Games): Games?  = dbQuery {
        val insertStatement = Game.insert {
            it[idTreasure] = gamesToAdd.idTreasure
            it[idUser] = gamesToAdd.idUser
            it[solved] = gamesToAdd.solved
            it[timeStart] = gamesToAdd.timeStart
            it[timeEnd] = gamesToAdd.timeEnd
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToGame)
    }

    override suspend fun putGame(gamesToUpdate: Games): Boolean = dbQuery {
        Game.update ({ Game.idGame eq gamesToUpdate.idGame } ){
            it[idTreasure] = gamesToUpdate.idTreasure
            it[idUser] = gamesToUpdate.idUser
            it[solved] = gamesToUpdate.solved
            it[timeStart] = gamesToUpdate.timeStart
            it[timeEnd] = gamesToUpdate.timeEnd
        } > 0
    }
    override suspend fun deleteGame(idGame: Int): Boolean  = dbQuery {
        Game.deleteWhere { Game.idGame eq idGame } > 0
    }
    override suspend fun deleteUserGames(idUser: Int): Boolean  = dbQuery {
        Game.deleteWhere { Game.idUser eq idUser } > 0
    }
    override suspend fun deleteTreasureGame(idTreasure: Int): Boolean  = dbQuery {
        Game.deleteWhere { Game.idTreasure eq idTreasure } > 0
    }
}