package com.lucascoelho.kotlinauthapi.domain.user_profile

import com.lucascoelho.kotlinauthapi.domain.user.Users
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "user_profile")
data class UserProfile(
    @Id
    @Column(name = "user_fk")
    val userId: UUID,
    @OneToOne
    @JoinColumn(name = "user_fk", referencedColumnName = "id", nullable = false)
    val user: Users,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val updatedAt: Date,
)
