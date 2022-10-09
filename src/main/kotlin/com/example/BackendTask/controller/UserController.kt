package com.example.BackendTask.controller

import com.example.BackendTask.request.RemoveRequest
import com.example.BackendTask.request.UserRequest
import com.example.BackendTask.response.UserResponse
import com.example.BackendTask.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
        private val userService: UserService
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    fun register(@RequestBody userRequest: UserRequest): UserResponse {
        return userService.register(userRequest)
    }

    @DeleteMapping("/users")
    fun remove(@RequestBody removeRequest: RemoveRequest){
        val user = userService.validateUser(removeRequest.email, removeRequest.password,"/members")
        userService.remove(user)
        return
    }
}