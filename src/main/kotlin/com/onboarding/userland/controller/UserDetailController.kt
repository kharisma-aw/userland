package com.onboarding.userland.controller

import com.onboarding.userland.dto.response.BasicInfoResponse
import com.onboarding.userland.service.UserDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/me"], produces = [APPLICATION_JSON_VALUE])
class UserDetailController {
    @Autowired
    lateinit var service: UserDetailService

    @GetMapping
    fun getDetail(@RequestParam("id") id: Long): BasicInfoResponse {
        return service.getUserDetail(id)
    }
}