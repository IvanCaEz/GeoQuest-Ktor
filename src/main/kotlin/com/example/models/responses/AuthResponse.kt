package com.example.models.responses

import com.example.models.User
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse (val token: String, val user: User)
