package com.example.BackendTask.controller

import com.example.BackendTask.exception.BlogException
import com.example.BackendTask.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class ExceptionHandler {


    @ExceptionHandler(BlogException::class)
    fun blogExceptionHandler(e:BlogException):ResponseEntity<ErrorResponse>{
        val errorResponse = ErrorResponse(
                time = e.errorDate,
                status = e.status.reasonPhrase,
                message = e.message,
                requestURI = e.requestURI
        )
        return ResponseEntity.status(e.status).body(errorResponse)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun MethodArgumentNotValidExceptionHandler(e: MethodArgumentNotValidException,
                                               request: HttpServletRequest): ResponseEntity<ErrorResponse?>? {
        val errorResponse = ErrorResponse(
                time = LocalDateTime.now(),
                status = HttpStatus.BAD_REQUEST.reasonPhrase,
                message = e.allErrors[0].defaultMessage,
                requestURI = request.requestURI
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }
}