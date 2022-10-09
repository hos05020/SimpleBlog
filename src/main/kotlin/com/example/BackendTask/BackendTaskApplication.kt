package com.example.BackendTask

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class BackendTaskApplication

fun main(args: Array<String>) {
	runApplication<BackendTaskApplication>(*args)
}
