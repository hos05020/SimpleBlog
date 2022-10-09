package com.example.BackendTask.controller

import com.example.BackendTask.request.CommentRequest
import com.example.BackendTask.request.RemoveRequest
import com.example.BackendTask.response.CommentResponse
import com.example.BackendTask.service.CommentService
import com.example.BackendTask.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
class CommentController(
        private val userService: UserService,
        private val commentService: CommentService
) {


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/articles/{articleId}/comments")
    fun register(@PathVariable articleId:Long,@Validated @RequestBody commentRequest: CommentRequest):CommentResponse{
        val user = userService.validateUser(commentRequest.email, commentRequest.password,"/article/$articleId/comments")
        return commentService.register(user,articleId,commentRequest.content, "/article/$articleId/comments")
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/articles/{articleId}/comments/{commentId}")
    fun edit(@PathVariable articleId: Long,@PathVariable commentId : Long,@Validated @RequestBody commentRequest: CommentRequest):CommentResponse{
        val user = userService.validateUser(commentRequest.email, commentRequest.password, "/article/$articleId/comments/$commentId")
        return commentService.edit(articleId,commentId,user,commentRequest.content,"/article/$articleId/comments/$commentId")
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/articles/{articleId}/comments/{commentId}")
    fun remove(@PathVariable articleId: Long,@PathVariable commentId : Long,@RequestBody removeRequest: RemoveRequest){
        val user = userService.validateUser(removeRequest.email, removeRequest.password, "/article/$articleId/comments/$commentId")
        return commentService.remove(articleId,commentId,user,"/article/$articleId/comments/$commentId")
    }
}