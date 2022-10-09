package com.example.BackendTask.request

import javax.validation.constraints.NotBlank

data class ArticleRequest(
        var email : String,
        var password: String,
        @field: NotBlank(message = "제목은 필수입니다.")
        var title: String,
        @field: NotBlank(message = "내용은 필수입니다.")
        var content: String,
) {
}