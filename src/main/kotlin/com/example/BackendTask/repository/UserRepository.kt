package com.example.BackendTask.repository

import com.example.BackendTask.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository:JpaRepository<User,Long> {

    fun findByEmail(email: String): User?

    fun findByEmailAndPassword(email: String, password: String): User?

}