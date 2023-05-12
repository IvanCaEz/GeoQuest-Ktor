package com.example.database

import com.example.models.*
import org.jetbrains.exposed.sql.ResultRow

interface FavouriteDAO {
    fun resultRowToFavourite(row: ResultRow) = Favourites(
        idUser = row[Favourite.idUser],
        idTreasure = row[Favourite.idTreasure],
    )

    suspend fun checkIfFavourite(userID: Int, treasureID: Int): Boolean

    suspend fun selectAllFavouritesByUserID(userID: Int): List<Favourites>
    suspend fun selectAllFavouritesByTreasureID(treasureID: Int): List<Favourites>

    suspend fun addFavourite(userID: Int, treasureID: Int): Favourites?
    suspend fun deleteFavouriteFromUser(userID: Int, treasureID: Int): Boolean
    suspend fun deleteFavouriteByTreasureID(treasureID: Int): Boolean
    suspend fun deleteUserFavorites(userID: Int): Boolean
}