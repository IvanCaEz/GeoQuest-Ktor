package com.example.database

import com.example.models.*
import org.jetbrains.exposed.sql.ResultRow
import java.sql.Timestamp

interface GameDAO {
    fun resultRowToGame(row: ResultRow) = Game(
        idGame = row[Games.idGame],
        idTreasure = row[Games.idTreasure],
        idUser = row[Games.idUser],
        solved = row[Games.solved],
        timeStart = row[Games.timeStart],
        timeEnd = row[Games.timeEnd],
        )
    suspend fun selectAllGames(): List<Game>
    suspend fun selectGameByGameID(idGame: Int): Game?
    suspend fun selectAllUserGames(idUser: Int): List<Game>?
    suspend fun selectAllTreasureGames(idTreasure: Int): List<Game>?

    //Si el user no ha jugado a ese tesoro se hace post, si ya lo ha jugado se hace put
    suspend fun postGame(gameToAdd: Game): Game?
    suspend fun putGame(gameToUpdate: Game): Boolean
    suspend fun deleteGame(idGame: Int): Boolean
    suspend fun deleteUserGames(idUser: Int): Boolean
    suspend fun deleteTreasureGame(idTreasure: Int): Boolean

}