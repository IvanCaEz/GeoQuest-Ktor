package com.example.models.CRUD

import com.example.database.DatabaseFactory
import com.example.database.DatabaseFactory.dbQuery
import com.example.database.ReviewDAO
import com.example.models.Reports
import com.example.models.Review
import com.example.models.Reviews
import com.example.models.Treasures
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ReviewCRUD: ReviewDAO {
    override suspend fun selectAllTreasureReviews(idTreasure: Int): List<Review> = dbQuery {
        Reviews
            .select { Reviews.idTreasure eq idTreasure }
            .map(::resultRowToReview)
    }

    // No sabemos si se va a utilizar
    override suspend fun selectAllTreasureReviewsByUser(idTreasure: Int, idUser: Int): List<Review> = dbQuery {
        Reviews
            .select { Reviews.idTreasure eq idTreasure and (Reviews.idUser eq idUser) }
            .map(::resultRowToReview)
    }

    override suspend fun postReview(reviewToAdd: Review): Review? = dbQuery {
        val insertStatement = Reviews.insert {
            it[idTreasure] = reviewToAdd.idTreasure
            it[idUser] = reviewToAdd.idUser
            it[opinion] = reviewToAdd.opinion
            it[rating] = reviewToAdd.rating
            it[photo] = reviewToAdd.photo
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToReview)
    }

    override suspend fun updateReview(reviewToUpdate: Review): Boolean = dbQuery {
        Reviews.update ({ Reviews.idReview eq reviewToUpdate.idReview }){
            it[opinion] = reviewToUpdate.opinion
            it[rating] = reviewToUpdate.rating
            it[photo] = reviewToUpdate.photo
        } > 0
    }

    override suspend fun deleteReview(idReview: Int): Boolean = dbQuery {
        Reviews.deleteWhere { Reviews.idReview eq idReview } > 0
    }
}