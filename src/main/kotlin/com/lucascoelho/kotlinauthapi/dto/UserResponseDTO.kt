package com.lucascoelho.kotlinauthapi.dto

data class UserResponseDTO (
    val token: String?,
    val message: String?,
    val success: Boolean
)