package com.lucascoelho.kotlinauthapi.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/health")
class HealthCheckController {
    @GetMapping("")
    @ResponseBody
    fun healthCheck(): ResponseEntity<Any> {
        data class HealthCheckDTO(val message: String, val time: Date)
        return ResponseEntity.ok().body(HealthCheckDTO(message = "OK", time = Date()))
    }
}