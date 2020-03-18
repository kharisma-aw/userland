package com.onboarding.userland.model

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.validator.constraints.Length
import java.sql.Date
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = -1,
        @CreationTimestamp
        val createdAt: Date = Date.valueOf(LocalDate.now()),
        @Column(length = 100)
        @Length(min = 4, max = 100)
        val fullname: String,
        @Column(length = 50, unique = true)
        @Length(min = 6, max = 50)
        val email: String,
        @Column(length = 100)
        val password: String,
        @Column(length = 100)
        val location: String? = null,
        @Column(length = 100)
        val bio: String? = null,
        @Column(length = 100)
        val web: String? = null,
        @Column(length = 100)
        val picture: String? = null
) {
    private constructor() : this(fullname = "test", email = "e@e.com", password = "password")
}