package com.example.Utils

import com.example.models.*
import com.google.gson.Gson
import kotlinx.datetime.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Serializer(forClass = Instant::class)
@OptIn(ExperimentalSerializationApi::class)
object InstantSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Instant", PrimitiveKind.LONG)
    override fun serialize(encoder: Encoder, value: Instant) = encoder.encodeLong(value.toEpochMilli())
    override fun deserialize(decoder: Decoder) = Instant.ofEpochMilli(decoder.decodeLong())
}

fun main() {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val dateString = currentDate.format(formatter)

    println(
        Json.encodeToString(
            Treasures(
                0, "ITB",
                "Tesoro en clase", "placeholder_map.png", 0.0, 0.0,
                "Barcelona", "Entre cables", "GOOD", "NOOB", 0.0
            )
        )
    )
    println(
        Json.encodeToString(
            Reviews(
                0, 3, 1,
                "Me ha costado encontrarlo", 3, "placeholder_review.png"
            )
        )
    )
    println(
        Json.encodeToString(
            Games(
                0,
                3,
                1,
                true,"",""

            )
        )
    )
    println(
        Json.encodeToString(
            Reports(
                0, 1, 3,
                "Está un poco roto", dateString
            )
        )
    )
    println(Json.encodeToString(Favourites(1, 3)))
}
