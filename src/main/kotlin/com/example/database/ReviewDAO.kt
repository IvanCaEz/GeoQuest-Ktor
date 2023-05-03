package com.example.database

import com.example.models.Reviews
import com.example.models.Review
import org.jetbrains.exposed.sql.ResultRow

interface ReviewDAO {
    fun resultRowToReview(row: ResultRow) = Reviews(
        idReview = row[Review.idReview],
        idTreasure = row[Review.idTreasure],
        idUser = row[Review.idUser],
        opinion = row[Review.opinion],
        rating = row[Review.rating],
        photo = row[Review.photo]
    )
    suspend fun selectAllTreasureReviews(idTreasure: Int): List<Reviews>
    suspend fun selectAllTreasureReviewsByUser(idUser: Int): List<Reviews>
    suspend fun selectReviewByUser(idUser: Int, idReview: Int): Reviews?
    suspend fun selectReviewOfTreasure(idTreasure: Int, idReview: Int): Reviews?

    suspend fun postReview(reviewsToAdd: Reviews): Reviews?
    suspend fun updateReview(reviewsToUpdate: Reviews): Boolean
    suspend fun deleteReview(idTreasure: Int, idReview: Int): Boolean
    suspend fun deleteReviewsOfTreasure(idTreasure: Int): Boolean
    suspend fun deleteReviewsOfUser(idUser: Int): Boolean

}