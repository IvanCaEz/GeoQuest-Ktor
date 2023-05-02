package com.example.models.CRUD

import com.example.database.DatabaseFactory.dbQuery
import com.example.database.UserDAO
import com.example.models.User
import com.example.models.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class UserCRUD: UserDAO {
    override suspend fun selectAllUsers(): List<User> = dbQuery{
        Users.selectAll().map(::resultRowToUser)
    }
    override suspend fun selectUserByID(idUser: Int): User? = dbQuery{
        Users
            .select { Users.idUser eq idUser }
            .map(::resultRowToUser)
            .singleOrNull()
    }
    override suspend fun selectUserByUserName(nickName: String): User? = dbQuery{
        Users
            .select { Users.nickName eq nickName }
            .map(::resultRowToUser)
            .singleOrNull()
    }
    override suspend fun addNewUser(nickname: String, email: String, password: String): User? = dbQuery {
            val insertStatement = Users.insert {
                it[nickName] = nickname
                it[Users.email] = email
                it[Users.password] = password
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }

    override suspend fun updateUser(userToUpdate: User): Boolean = dbQuery {
        Users.update ({ Users.idUser eq userToUpdate.idUser } ){
            it[nickName] = userToUpdate.nickName
            it[email] = userToUpdate.email
            it[password] = userToUpdate.password
            it[photo] = userToUpdate.photo
            it[userLevel] = userToUpdate.userLevel
            it[userRole] = userToUpdate.userRole
        } > 0
    }

    override suspend fun deleteUser(idUser: Int): Boolean = dbQuery {
        Users.deleteWhere { Users.idUser eq idUser } > 0
    }
}