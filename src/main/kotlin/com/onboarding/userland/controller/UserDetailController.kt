package com.onboarding.userland.controller

import com.onboarding.userland.dto.request.BasicInfoRequest
import com.onboarding.userland.dto.response.BasicInfoResponse
import com.onboarding.userland.dto.response.GeneralSuccessResponse
import com.onboarding.userland.service.UserDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/me"], produces = [APPLICATION_JSON_VALUE])
class UserDetailController {
    @Autowired
    lateinit var service: UserDetailService

    @GetMapping
    fun getDetail(@RequestParam("id") id: Long): BasicInfoResponse {
        return service.getUserDetail(id)
    }

    @PostMapping
    fun updateBasicInfo(@RequestBody @Valid basicInfo: BasicInfoRequest): GeneralSuccessResponse {
        return service.updateBasicInfo(basicInfo)
    }

    @PostMapping
    fun updatePicture(@RequestBody @Valid url: String): GeneralSuccessResponse {
        return service.updatePicture(url)
    }

    @PostMapping
    fun updateEmail(@RequestBody @Valid email: String): GeneralSuccessResponse {
        return service.updateEmail(email)
    }
}