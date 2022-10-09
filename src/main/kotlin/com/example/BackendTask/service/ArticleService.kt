package com.example.BackendTask.service

import com.example.BackendTask.domain.Article
import com.example.BackendTask.domain.User
import com.example.BackendTask.exception.BlogException
import com.example.BackendTask.repository.ArticleRepository
import com.example.BackendTask.response.ArticleResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class ArticleService(
        private val articleRepository: ArticleRepository
) {


    @Transactional
    fun register(user:User,title:String,content:String):ArticleResponse{
        val article = Article(
                title = title,
                content = content,
                user = user
        )

        articleRepository.save(article)
        user.addArticle(article)
        return ArticleResponse(
                articleId = article.id,
                email = user.email,
                title = title,
                content = content
        )
    }

    @Transactional
    fun modify(articleId:Long,user: User,title: String,content: String,requestURI:String):ArticleResponse{
         val article = getArticle(articleId,requestURI)
         isValid(article,user,requestURI)
        article.editArticle(title,content)
        return ArticleResponse(
                articleId = article.id,
                email = user.email,
                title = title,
                content = content
        )
    }

    @Transactional
    fun remove(articleId: Long,user: User,requestURI: String){
        val article = getArticle(articleId, requestURI)
        isValid(article,user,requestURI)
        articleRepository.delete(article)
    }


    fun getArticle(articleId: Long,requestURI: String):Article{
        val article = articleRepository.findByIdOrNull(articleId)
        return article?:throw BlogException("게시글을 찾을 수 없습니다.", LocalDateTime.now(), HttpStatus.NOT_FOUND,requestURI)
    }

    fun isValid(article: Article,user: User,requestURI: String){
        if(!article.user.isSame(user)){
            throw BlogException("접근 권한이 없습니다", LocalDateTime.now(),HttpStatus.FORBIDDEN,requestURI)
        }
    }

}