package com.onboarding.userland.repository

import com.onboarding.userland.model.User
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface UserRepository : CrudRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    fun findByEmail(@Param("email") email: String): User?

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.fullname = :fullname, u.location = :location, u.bio = :bio, u.web = :web WHERE u.id = 1")
    fun updateBasicInfo(@Param("fullname") fullname: String,
                        @Param("location") location: String,
                        @Param("bio") bio: String,
                        @Param("web") web: String
    ): Int
}