package com.example.database

import com.example.models.User
import com.example.models.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert

interface UserDAO {
     fun resultRowToUser(row: ResultRow) = User(
        idUser = row[Users.idUser],
        nickName = row[Users.nickName],
        email = row[Users.email],
        password = row[Users.password],
        photo = row[Users.photo],
        userLevel = row[Users.userLevel],
        userRole = row[Users.userRole],
        favs = listOf()
        )

    suspend fun selectAllUsers(): List<User>
    suspend fun selectUserByID(idUser: Int): User?
    suspend fun selectUserByUserName(nickName: String): User?
    suspend fun addNewUser(nickname: String, email: String, password:String): User?
    suspend fun updateUser(userToUpdate: User): Boolean
    suspend fun deleteUser(idUser: Int): Boolean
    suspend fun checkIfUserExistByNick(nickName: String): Boolean


}