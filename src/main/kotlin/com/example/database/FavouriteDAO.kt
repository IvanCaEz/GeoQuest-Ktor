package com.example.database

import com.example.models.*
import org.jetbrains.exposed.sql.ResultRow

interface FavouriteDAO {
    fun resultRowToFavourite(row: ResultRow) = Favourite(
        idUser = row[Favourites.idUser],
        idTreasure = row[Favourites.idTreasure],
    )

    suspend fun selectAllFavourites(userID: Int): List<Favourite>
    suspend fun addFavourite(userID: Int, treasureID: Int): Favourite?
    suspend fun deleteFavourite(userID: Int, treasureID: Int): Boolean
}