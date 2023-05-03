package com.example.models.CRUD

import com.example.database.DatabaseFactory.dbQuery
import com.example.database.ReviewDAO
import com.example.models.Reviews
import com.example.models.Review
import com.example.models.Treasure
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ReviewCRUD: ReviewDAO {
    override suspend fun selectAllTreasureReviews(idTreasure: Int): List<Reviews> = dbQuery {
        Review
            .select { Review.idTreasure eq idTreasure }
            .map(::resultRowToReview)
    }

    override suspend fun selectAllTreasureReviewsByUser(idUser: Int): List<Reviews> = dbQuery {
        Review
            .select {Review.idUser eq idUser }
            .map(::resultRowToReview)
    }

    override suspend fun selectReviewByUser(idUser: Int, idReview: Int): Reviews? = dbQuery {
        Review
            .select { (Review.idUser eq idUser) and (Review.idReview eq idReview) }
            .map(::resultRowToReview)
            .singleOrNull()
    }

    override suspend fun selectReviewOfTreasure(idTreasure: Int, idReview: Int): Reviews? = dbQuery {
        Review
            .select { (Review.idTreasure eq idTreasure) and (Review.idReview eq idReview) }
            .map(::resultRowToReview)
            .singleOrNull()
    }

    override suspend fun postReview(reviewsToAdd: Reviews): Reviews? = dbQuery {
        val insertStatement = Review.insert {
            it[idTreasure] = reviewsToAdd.idTreasure
            it[idUser] = reviewsToAdd.idUser
            it[opinion] = reviewsToAdd.opinion
            it[rating] = reviewsToAdd.rating
            it[photo] = reviewsToAdd.photo
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToReview)
    }

    override suspend fun updateReview(reviewsToUpdate: Reviews): Boolean = dbQuery {
        Review.update ({ Review.idReview eq reviewsToUpdate.idReview }){
            it[opinion] = reviewsToUpdate.opinion
            it[rating] = reviewsToUpdate.rating
            it[photo] = reviewsToUpdate.photo
        } > 0
    }

    override suspend fun deleteReview(idTreasure: Int, idReview: Int): Boolean = dbQuery {
        Review.deleteWhere { (Review.idReview eq idReview) and
                    (Review.idTreasure eq idTreasure) } > 0
    }

    override suspend fun deleteReviewsOfTreasure(idTreasure: Int): Boolean = dbQuery {
        Review.deleteWhere { Review.idTreasure eq idTreasure } > 0
    }

    override suspend fun deleteReviewsOfUser(idUser: Int): Boolean = dbQuery {
        Review.deleteWhere { Review.idUser eq idUser } > 0
    }

}