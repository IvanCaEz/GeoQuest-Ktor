package com.example.database

import com.example.models.*
import org.jetbrains.exposed.sql.ResultRow

interface GameDAO {
    fun resultRowToGame(row: ResultRow) = Games(
        idGame = row[Game.idGame],
        idTreasure = row[Game.idTreasure],
        idUser = row[Game.idUser],
        solved = row[Game.solved],
        timeStart = row[Game.timeStart],
        timeEnd = row[Game.timeEnd],
        )
    suspend fun selectAllGames(): List<Games>
    suspend fun selectGameByGameID(idGame: Int): Games?
    suspend fun selectAllUserGames(idUser: Int): List<Games>?
    suspend fun selectAllTreasureGames(idTreasure: Int): List<Games>?

    //Si el user no ha jugado a ese tesoro se hace post, si ya lo ha jugado se hace put
    suspend fun postGame(gamesToAdd: Games): Games?
    suspend fun putGame(gamesToUpdate: Games): Boolean
    suspend fun deleteGame(idGame: Int): Boolean
    suspend fun deleteUserGames(idUser: Int): Boolean
    suspend fun deleteTreasureGame(idTreasure: Int): Boolean



}