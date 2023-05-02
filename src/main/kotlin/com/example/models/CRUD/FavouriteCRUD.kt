package com.example.models.CRUD

import com.example.database.DatabaseFactory.dbQuery
import com.example.database.FavouriteDAO
import com.example.models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.selectAll

class FavouriteCRUD: FavouriteDAO {
    override suspend fun selectAllFavouritesByUserID(userID: Int): List<Favourite> = dbQuery {
        Favourites.select {Favourites.idUser eq userID}.map(::resultRowToFavourite)
    }

    override suspend fun selectAllFavouritesByTreasureID(treasureID: Int): List<Favourite> = dbQuery {
        Favourites.select {Favourites.idTreasure eq treasureID}.map(::resultRowToFavourite)

    }

    override suspend fun addFavourite(userID: Int, treasureID: Int): Favourite? = dbQuery {
        val insertStatement = Favourites.insert {
            it[idUser] = userID
            it[idTreasure] = treasureID
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToFavourite)
    }
    override suspend fun deleteFavourite(userID: Int, treasureID: Int): Boolean = dbQuery {
        Favourites.deleteWhere{ (idTreasure eq treasureID) and (idUser eq userID)  } > 0
    }
}