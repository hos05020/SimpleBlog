package com.example.BackendTask.exception

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class BlogException(
        message:String?,
        val errorDate:LocalDateTime,
        val status: HttpStatus,
        val requestURI: String
):RuntimeException(message) {

}