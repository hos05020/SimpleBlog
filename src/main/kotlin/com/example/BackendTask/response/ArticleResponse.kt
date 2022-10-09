package com.example.BackendTask.response

data class ArticleResponse(
        var articleId:Long?,
        var email:String,
        var title:String,
        var content:String,
) {
}