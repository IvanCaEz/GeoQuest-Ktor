package com.example.models.CRUD

import com.example.database.DatabaseFactory.dbQuery
import com.example.database.FavouriteDAO
import com.example.models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class FavouriteCRUD: FavouriteDAO {
    override suspend fun checkIfFavourite(userID: Int, treasureID: Int): Boolean  = dbQuery {
      Favourite.select{(Favourite.idTreasure eq treasureID) and (Favourite.idUser eq userID)}.empty()
    }

    override suspend fun selectAllFavouritesByUserID(userID: Int): List<Favourites> = dbQuery {
        Favourite.select {Favourite.idUser eq userID}.map(::resultRowToFavourite)
    }

    override suspend fun selectAllFavouritesByTreasureID(treasureID: Int): List<Favourites> = dbQuery {
        Favourite.select {Favourite.idTreasure eq treasureID}.map(::resultRowToFavourite)

    }

    override suspend fun addFavourite(userID: Int, treasureID: Int): Favourites? = dbQuery {
        val insertStatement = Favourite.insert {
            it[idUser] = userID
            it[idTreasure] = treasureID
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToFavourite)
    }
    override suspend fun deleteFavourite(userID: Int, treasureID: Int): Boolean = dbQuery {
        Favourite.deleteWhere{ (idTreasure eq treasureID) and (idUser eq userID)  } > 0
    }
}