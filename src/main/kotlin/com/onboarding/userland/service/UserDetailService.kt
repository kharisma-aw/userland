package com.onboarding.userland.service

import com.onboarding.userland.dto.request.BasicInfoRequest
import com.onboarding.userland.dto.response.BasicInfoResponse
import com.onboarding.userland.dto.response.GeneralSuccessResponse
import com.onboarding.userland.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired

class UserDetailService {
    @Autowired
    lateinit var repository: UserRepository

    fun getUserDetail(id: Long): BasicInfoResponse {
        val user = repository.findById(id).get()
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

    fun updateBasicInfo(basicInfo: BasicInfoRequest): GeneralSuccessResponse {
        return with(basicInfo) {
            val num = repository.updateBasicInfo(fullname, location ?: "", bio ?: "", web ?: "")
            if (num == 1) GeneralSuccessResponse else throw RuntimeException()
        }
    }
}