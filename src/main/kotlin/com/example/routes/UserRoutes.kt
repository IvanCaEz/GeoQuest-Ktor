package com.example.routes

import com.example.models.Game
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

fun Route.userRouting(){
    route("/user"){
        post ("/login"){
            // recibimos este formato username,password
            val userCredentials = call.receive<String>()
            val userName = userCredentials.split(",")[0]
            val password = userCredentials.split(",")[1]

            // buscamos en la base de datos, si hay un user con estas credenciales
            //hacer un if buscando estas credenciales
            //si lo hay es correcto y se logea y sino pues no
        }

        post {
            val newUser = call.receive<User>()


        }

        get("{userID}"){
            val userID = call.parameters["userID"]
            if (userID.isNullOrBlank()) return@get call.respondText("Missing user id.",
                status = HttpStatusCode.BadRequest)

            // buscar en la bd con el userid y retornar el user si lo hay
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
                            if (userToUpdate.photo != "buscar en base de datos"){
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
            //userToUpdate.favs = llamada a base de datos para recoger los favs
            // buscar en la bd con el userid y updatear el user
        }
        delete("{userID}"){
            val userID = call.parameters["userID"]
            if (userID.isNullOrBlank()) return@delete call.respondText("Missing user id.",
                status = HttpStatusCode.BadRequest)

            // eliminar todas las cosas que tenga el user asociadas (reviews, games, stats, reports)
            // antes de eliminar el user
        }

        get("{userName}"){
            val userName = call.parameters["userName"]
            if (userName.isNullOrBlank()) return@get call.respondText("Missing username.",
                status = HttpStatusCode.BadRequest)

            // buscar en la bd con el userName y retornar el user si lo hay
        }

        get("{userID}/picture"){
            var userImage: File = File("")
            val userID = call.parameters["userID"]
            if (userID.isNullOrBlank()) return@get call.respondText("Missing user id.",
                status = HttpStatusCode.BadRequest)
            val userPhoto = ""

            //val user = recuperamos el user de la base de datos
            userImage = File("src/main/kotlin/com/example/user_pictures/"+userPhoto)
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

            //val user = recuperamos el user de la base de datos
            // buscar en la BBDD los juegos con el id del usuario y actualizar el userStats
            // buscar en la BBDD los reports con el id del usuario
            val gamesPlayed = listOf<Game>() // hacer counts/average en las querys


        }





    }

}