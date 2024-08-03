package com.lucascoelho.kotlinauthapi.domain.user

data class UserResponseDTO (
    val token: String?,
    val message: String?,
    val success: Boolean
)