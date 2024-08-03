package com.lucascoelho.kotlinauthapi.domain.user

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity
@Table(name = "users")
data class Users (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID,
    @Column(name = "username")
    val login: String,
    @Column(name = "password")
    val userPassword: String,
    var lastLogin: Date? = null,
    var createdAt: Date? = null,
    var deletedAt: Date? = null,

    val enabled: Boolean
): UserDetails {

    constructor(login: String, userPassword: String): this(UUID.randomUUID(), login, userPassword, null, null, null, true)

    @PrePersist
    fun prePersist() {
        createdAt = Date()
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getPassword(): String {
        return userPassword
    }

    override fun getUsername(): String {
        return login
    }
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return enabled
    }
}
