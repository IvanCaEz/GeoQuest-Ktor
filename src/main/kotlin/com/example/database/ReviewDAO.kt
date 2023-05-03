package com.example.database

import com.example.models.Report
import com.example.models.Reports
import com.example.models.Review
import com.example.models.Reviews
import org.jetbrains.exposed.sql.ResultRow

interface ReviewDAO {
    fun resultRowToReview(row: ResultRow) = Review(
        idReview = row[Reviews.idReview],
        idTreasure = row[Reviews.idTreasure],
        idUser = row[Reviews.idUser],
        opinion = row[Reviews.opinion],
        rating = row[Reviews.rating],
        photo = row[Reviews.photo]
    )
    suspend fun selectAllTreasureReviews(idTreasure: Int): List<Review>
    suspend fun selectAllTreasureReviewsByUser(idTreasure: Int, idUser: Int): List<Review>
    suspend fun postReview(reviewToAdd: Review): Review?
    suspend fun updateReview(reviewToUpdate: Review): Boolean
    suspend fun deleteReview(idReview: Int): Boolean
}