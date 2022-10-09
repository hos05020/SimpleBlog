package com.example.BackendTask.response

import java.time.LocalDateTime

data class ErrorResponse(
        val time:LocalDateTime,
        val status: String,
        val message: String?,
        val requestURI:String
) {
}