package com.example.models.CRUD

import com.example.database.DatabaseFactory.dbQuery
import com.example.database.FavouriteDAO
import com.example.database.GameDAO
import com.example.models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.selectAll
import java.sql.Timestamp
import java.time.Duration
import java.util.*

class GameCRUD: GameDAO {
    override suspend fun selectAllGames(): List<Game>  = dbQuery {
        Games.selectAll().map(::resultRowToGame)
    }

    override suspend fun selectGameByGameID(idGame: Int): Game? = dbQuery {
        Games
            .select { Games.idGame eq idGame }
            .map(::resultRowToGame)
            .singleOrNull()
    }

    override suspend fun selectAllUserGames(idUser: Int): List<Game> = dbQuery {
        Games
            .select { Games.idUser eq idUser }
            .map(::resultRowToGame)
    }

    override suspend fun selectAllTreasureGames(idTreasure: Int): List<Game> = dbQuery {
        Games
            .select { Games.idTreasure eq idTreasure }
            .map(::resultRowToGame)
    }




    override suspend fun postGame(gameToAdd: Game): Game?  = dbQuery {
        val insertStatement = Games.insert {
            it[idTreasure] = gameToAdd.idTreasure
            it[idUser] = gameToAdd.idUser
            it[solved] = gameToAdd.solved
            it[timeStart] = gameToAdd.timeStart
            it[timeEnd] = gameToAdd.timeEnd
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToGame)
    }

    override suspend fun putGame(gameToUpdate: Game): Boolean = dbQuery {
        Games.update ({ Games.idGame eq gameToUpdate.idGame } ){
            it[idTreasure] = gameToUpdate.idTreasure
            it[idUser] = gameToUpdate.idUser
            it[solved] = gameToUpdate.solved
            it[timeStart] = gameToUpdate.timeStart
            it[timeEnd] = gameToUpdate.timeEnd
        } > 0
    }
    override suspend fun deleteGame(idGame: Int): Boolean  = dbQuery {
        Games.deleteWhere { Games.idGame eq idGame } > 0
    }
    override suspend fun deleteUserGames(idUser: Int): Boolean  = dbQuery {
        Games.deleteWhere { Games.idUser eq idUser } > 0
    }
    override suspend fun deleteTreasureGame(idTreasure: Int): Boolean  = dbQuery {
        Games.deleteWhere { Games.idTreasure eq idTreasure } > 0
    }


}