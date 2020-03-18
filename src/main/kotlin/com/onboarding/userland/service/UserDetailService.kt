package com.onboarding.userland.service

import com.onboarding.userland.dto.request.BasicInfoRequest
import com.onboarding.userland.dto.response.BasicInfoResponse
import com.onboarding.userland.dto.response.GeneralSuccessResponse
import com.onboarding.userland.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.persistence.EntityNotFoundException

@Component
class UserDetailService @Autowired constructor(private val repository: UserRepository) {
    fun getUserDetail(email: String): BasicInfoResponse {
        val user = repository.findByEmail(email) ?: throw EntityNotFoundException()
        return with(user) {
            BasicInfoResponse(
                    this.id,
                    fullname,
                    location,
                    bio,
                    web,
                    picture,
                    createdAt.toString()
            )
        }
    }

    fun updateBasicInfo(email: String, basicInfo: BasicInfoRequest): GeneralSuccessResponse {
        return with(basicInfo) {
            val num = repository.updateBasicInfo(email, fullname, location ?: "", bio ?: "", web ?: "")
            if (num == 1) GeneralSuccessResponse else throw RuntimeException()
        }
    }

    fun updatePicture(email: String, url: String): GeneralSuccessResponse {
        val num = repository.updatePicture(email, url)
        return if (num == 1) GeneralSuccessResponse else throw RuntimeException()
    }

    fun updateEmail(email: String, newEmail: String): GeneralSuccessResponse {
        val num = repository.updateEmail(email, newEmail)
        return if (num == 1) GeneralSuccessResponse else throw RuntimeException()
    }

    fun changePassword(email: String, newPassword: String): GeneralSuccessResponse {
        val num = repository.updatePassword(email, newPassword)
        return if (num == 1) GeneralSuccessResponse else throw RuntimeException()
    }

    fun validatePassword(email: String, password: String): Boolean {
        val savedPassword = repository.getPassword(email)
        return password == savedPassword
    }
}