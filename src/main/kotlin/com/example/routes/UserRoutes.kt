package com.example.routes

import com.example.models.CRUD.FavouriteCRUD
import com.example.models.CRUD.GameCRUD
import com.example.models.CRUD.TreasureCRUD
import com.example.models.CRUD.UserCRUD
import com.example.models.Game
import com.example.models.Treasure
import com.example.models.User
import com.example.models.UserStats
import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.io.FileNotFoundException

fun Route.userRouting() {
    val userCrud = UserCRUD()
    val favCrud = FavouriteCRUD()
    val treasureCrud = TreasureCRUD()
    route("/user") {
        post("/login") {
            // recibimos este formato username,password
            val userCredentials = call.receive<String>()
            val userName = userCredentials.split(",")[0]
            val password = userCredentials.split(",")[1]

            if (userName == ""){
                return@post call.respondText("Username can't be empty.",
                    status = HttpStatusCode.BadRequest)
            }
            if (password == ""){
                return@post call.respondText("Password can't be empty.",
                    status = HttpStatusCode.BadRequest)
            }
            val userLogging = userCrud.selectUserByUserName(userName)
            if (userLogging != null) {
                if (userLogging.password == password){
                    return@post call.respondText("idUser: ${userLogging.idUser}",
                        status = HttpStatusCode.OK
                    )
                } else return@post call.respondText("Incorrect password.",
                        status = HttpStatusCode.Unauthorized
                )
            } else return@post call.respondText("User with username $userName not found.",
                status = HttpStatusCode.NotFound)
        }

        post {
            val newUser = call.receive<User>()
            val userIfExist = userCrud.checkIfUserExistByNick(newUser.nickName)
            if (userIfExist) {
                userCrud.addNewUser(newUser.nickName, newUser.email, newUser.password)
                call.respondText("User stored correctly.", status = HttpStatusCode.Created)
            } else call.respondText("User already exist.", status = HttpStatusCode.OK)
        }

        get("{userID}"){
            val userID = call.parameters["userID"]
            if (userID.isNullOrBlank()) return@get call.respondText("Missing user id.",
                status = HttpStatusCode.BadRequest)
            val user = userCrud.selectUserByID(userID.toInt())
            if (user != null){
                return@get call.respond(user)
            } else return@get call.respondText("User with id $userID not found.",
                status = HttpStatusCode.NotFound)
        }

        put("{userID}"){
            val userID = call.parameters["userID"]
            if (userID.isNullOrBlank()) return@put call.respondText("Missing user id.",
                status = HttpStatusCode.BadRequest)

            val userData = call.receiveMultipart()
            var userToUpdate = User(userID.toInt(), "", "", "", "","",
                "", listOf())
            val gson = Gson()
            userData.forEachPart { part ->
                when (part){
                    is PartData.FormItem -> {
                        userToUpdate = gson.fromJson(part.value, User::class.java)
                    }
                    is PartData.FileItem -> {
                        try {
                            userToUpdate.photo = part.originalFileName as String
                            if (userToUpdate.photo != userCrud.selectUserByID(userID.toInt())!!.photo){
                                val fileBytes = part.streamProvider().readBytes()
                                File("src/main/kotlin/com/example/user_pictures/"+userToUpdate.photo)
                                    .writeBytes(fileBytes)
                            }
                        } catch (e: FileNotFoundException){
                            println("Error ${e.message}")
                        }
                    }
                    else -> {}
                }
                println("Subido ${part.name}")
            }

            val favouriteTreasuresID = favCrud.selectAllFavouritesByUserID(userID.toInt())
            val favTreasures = mutableListOf<Treasure>()

            favouriteTreasuresID.forEach { fav ->
                favTreasures.add(treasureCrud.selectTreasureByID(fav.idTreasure)!!)
            }
            userToUpdate.favs = favTreasures

            userCrud.updateUser(userToUpdate)
            return@put call.respondText(
                "User with id $userID has been updated.", status = HttpStatusCode.Accepted
            )
        }
        delete("{userID}"){
            val userID = call.parameters["userID"]
            if (userID.isNullOrBlank()) return@delete call.respondText("Missing user id.",
                status = HttpStatusCode.BadRequest)

            // Primero eliminamos los favoritos, los games (las reviews) y los reports
            val favList = favCrud.selectAllFavouritesByUserID(userID.toInt())
            favList.forEach { fav ->
                favCrud.deleteFavourite(userID.toInt(), fav.idTreasure)
            }
            //TODO() Eliminar games, reviews y reports

            // Luego eliminamos el user
            GameCRUD().deleteUserGames(userID.toInt())
            userCrud.deleteUser(userID.toInt())
            call.respondText("User with id $userID has been deleted.",
                status = HttpStatusCode.OK)

        }

        get("{userName}"){
            val userName = call.parameters["userName"]
            if (userName.isNullOrBlank()) return@get call.respondText("Missing username.",
                status = HttpStatusCode.BadRequest)

            val user = userCrud.selectUserByUserName(userName)
            if (user != null){
                return@get call.respond(user)
            } else return@get call.respondText("User with username $userName not found.",
                status = HttpStatusCode.NotFound)
        }

        get("{userID}/picture"){
            var userImage: File = File("")
            val userID = call.parameters["userID"]
            if (userID.isNullOrBlank()) return@get call.respondText("Missing user id.",
                status = HttpStatusCode.BadRequest)

            val userPhoto = userCrud.selectUserByID(userID.toInt())!!.photo
            userImage = File("src/main/kotlin/com/example/user_pictures/$userPhoto")
            if (userImage.exists()){
                call.respondFile(userImage)
            } else {
                call.respondText("No image found.", status = HttpStatusCode.NotFound)
            }
        }

        get("{userID}/stats"){
            val userID = call.parameters["userID"]
            if (userID.isNullOrBlank()) return@get call.respondText("Missing user id.",
                status = HttpStatusCode.BadRequest)
            val userStats = UserStats(userID.toInt(), 0,0,0,0.0)

            val user = userCrud.selectUserByID(userID.toInt())

            // buscar en la BBDD los juegos con el id del usuario y actualizar el userStats
            // buscar en la BBDD los reports con el id del usuario
            val gamesPlayed = listOf<Game>() // hacer counts/average en las querys
            //TODO()

        }


        get("{userID}/treasures"){
            val userID = call.parameters["userID"]
            if (userID.isNullOrBlank()) return@get call.respondText("Missing user id.",
                status = HttpStatusCode.BadRequest)
            val userStats = UserStats(userID.toInt(), 0,0,0,0.0)

            val user = userCrud.selectUserByID(userID.toInt())
                 //TODO()


        }


        get("{userID}/favs"){
            val userID = call.parameters["userID"]
            if (userID.isNullOrBlank()) return@get call.respondText("Missing user id.",
                status = HttpStatusCode.BadRequest)
            val favList = favCrud.selectAllFavouritesByUserID(userID.toInt())
            if (favList.isNotEmpty()){
                call.respond(favList)
            } else call.respondText("User with id $userID hasn't mark any treasure as favourite.")
        }
        post("{userID}/favs"){
            val userID = call.parameters["userID"]
            if (userID.isNullOrBlank()) return@post call.respondText("Missing user id.",
                status = HttpStatusCode.BadRequest)
            val treasureID = call.receive<Treasure>().idTreasure
            favCrud.addFavourite(userID.toInt(), treasureID)
            call.respondText("Treasure with id $treasureID added to user with id $userID list of favorites.")
        }
        delete("{userID}/favs/{treasureID}"){
            val userID = call.parameters["userID"]
            val treasureID = call.parameters["treasureID"]
            if (userID.isNullOrBlank()) return@delete call.respondText("Missing user id.",
                status = HttpStatusCode.BadRequest)
            if (treasureID.isNullOrBlank()) return@delete call.respondText("Missing treasure id.",
                status = HttpStatusCode.BadRequest)
            favCrud.deleteFavourite(userID.toInt(), treasureID.toInt())
            call.respondText("Treasure with id $treasureID deleted from user with id $userID list of favorites.")
        }

    }

}