package com.example.BackendTask.response

data class CommentResponse(
        var commentId:Long?,
        var email:String,
        var content:String
) {
}