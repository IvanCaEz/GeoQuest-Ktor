package com.example.models.CRUD

import com.example.database.DatabaseFactory.dbQuery
import com.example.database.FavouriteDAO
import com.example.database.GameDAO
import com.example.models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.selectAll

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

    override suspend fun selectAllUserGames(idUser: Int): List<Game>? {
        TODO("Not yet implemented")
    }

    override suspend fun selectAllTreasureGames(idTreasure: Int): List<Game>? {
        TODO("Not yet implemented")
    }

    override suspend fun postGame(gameToAdd: Game): Game? {
        TODO("Not yet implemented")
    }

    override suspend fun putGame(gameToUpdate: Game): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGame(idGame: Int): Boolean {
        TODO("Not yet implemented")
    }

}