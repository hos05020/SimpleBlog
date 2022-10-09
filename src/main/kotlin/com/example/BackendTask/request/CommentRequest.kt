package com.example.BackendTask.request

import javax.validation.constraints.NotBlank

data class CommentRequest(
        var email:String,
        var password:String,
        @field:NotBlank(message = "내용은 필수입니다.")
        var content:String
) {
}