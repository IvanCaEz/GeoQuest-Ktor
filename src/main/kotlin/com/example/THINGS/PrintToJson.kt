package com.example.THINGS

import com.example.models.*
import kotlinx.datetime.Month
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset


fun main () {
    println(Json.encodeToString(Treasures(0, "ITB",
        "Tesoro en clase", "placeholder_map.png", 0.0, 0.0,
        "Barcelona", "Entre cables", "GOOD", "NOOB", 0.0)))
    println(Json.encodeToString(Reviews(0, 3, 1,
            "Me ha costado encontrarlo", 3, "placeholder_review.png")))
    println(Json.encodeToString(
        Games(0, 3, 1,
        true, LocalDateTime.now().toInstant(ZoneOffset.UTC), LocalDateTime.of(2023, Month.MAY, 3, 16, 8, 18).toInstant(ZoneOffset.UTC))
    ))
    println(Json.encodeToString(Reports(0, 1, 3,
        "Está un poco roto", LocalDate.now())))
    println(Json.encodeToString(Favourites(1, 3)))
}