package com.example.models.CRUD

import com.example.database.DatabaseFactory.dbQuery
import com.example.database.FavouriteDAO
import com.example.models.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class FavouriteCRUD: FavouriteDAO {
    override suspend fun selectAllFavourites(userID: Int): List<Favourite> = dbQuery {
        Favourites.selectAll().map(::resultRowToFavourite)
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