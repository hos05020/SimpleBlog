package com.example.BackendTask.controller

import com.example.BackendTask.request.ArticleRequest
import com.example.BackendTask.request.RemoveRequest
import com.example.BackendTask.response.ArticleResponse
import com.example.BackendTask.service.ArticleService
import com.example.BackendTask.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
class ArticleController(
        private val articleService: ArticleService,
        private val userService: UserService
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/articles")
    fun register(@Validated @RequestBody articleRequest: ArticleRequest): ArticleResponse {
        val user = userService.validateUser(articleRequest.email, articleRequest.password, "/articles")
        return articleService.register(user, articleRequest.title, articleRequest.content)
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/articles/{articleId}")
    fun edit(@PathVariable articleId: Long, @Validated @RequestBody articleRequest: ArticleRequest): ArticleResponse {
        val user = userService.validateUser(articleRequest.email, articleRequest.password, "/articles")
        return articleService.modify(articleId, user, articleRequest.title, articleRequest.content, "/articles/$articleId")
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/articles/{articleId}")
    fun remove(@PathVariable articleId: Long, @RequestBody removeRequest: RemoveRequest){
        val user = userService.validateUser(removeRequest.email, removeRequest.password, "/articles")
        return articleService.remove(articleId, user, "/articles/$articleId")
    }


}