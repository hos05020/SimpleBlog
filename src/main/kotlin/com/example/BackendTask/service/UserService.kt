package com.example.BackendTask.service

import com.example.BackendTask.domain.User
import com.example.BackendTask.exception.BlogException
import com.example.BackendTask.repository.UserRepository
import com.example.BackendTask.request.UserRequest
import com.example.BackendTask.response.UserResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional(readOnly = true)
class UserService(
        private val userRepository: UserRepository,
) {


    @Transactional(readOnly = false)
    fun register(userRequest: UserRequest):UserResponse{
        validateDuplicateRegister(userRequest)
        val user = User(
                email = userRequest.email,
                password = userRequest.password,
                username = userRequest.username
        )

        userRepository.save(user)
        return UserResponse(user.email,user.username)
    }

    fun validateDuplicateRegister(userRequest: UserRequest) {
        val user = userRepository.findByEmail(userRequest.email)
        if(user!=null){
            throw BlogException("중복된 회원입니다", LocalDateTime.now(),HttpStatus.CONFLICT,"/members")
        }
    }

    fun validateUser(email:String,password:String,requestURI:String):User{
        val user = userRepository.findByEmailAndPassword(email,password)
        return user ?: throw BlogException("아이디 혹은 비밀번호를 확인하세요", LocalDateTime.now(),HttpStatus.UNAUTHORIZED,requestURI)
    }

    @Transactional(readOnly = false)
    fun remove(user: User){
        userRepository.delete(user)
    }
}