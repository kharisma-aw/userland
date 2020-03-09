package com.onboarding.userland.model

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.validator.constraints.UniqueElements
import java.sql.Date
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = -1,
        @CreationTimestamp
        val createdAt: Date = Date.valueOf(LocalDate.now()),
        val fullname: String,
        val email: String,
        val password: String,
        val location: String? = null,
        val bio: String? = null,
        val web: String? = null,
        val picture: String? = null
) {
    private constructor() : this(fullname = "", email = "", password = "")
}