package com.example.BackendTask.service

import com.example.BackendTask.domain.Comment
import com.example.BackendTask.domain.User
import com.example.BackendTask.exception.BlogException
import com.example.BackendTask.repository.ArticleRepository
import com.example.BackendTask.repository.CommentRepository
import com.example.BackendTask.response.CommentResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class CommentService(
        private val commentRepository: CommentRepository,
        private val articleRepository: ArticleRepository
) {

    @Transactional(readOnly = false)
    fun register(user:User,articleId:Long,content:String,requestURI:String):CommentResponse{
        val article = articleRepository.findByIdOrNull(articleId) ?: throw BlogException("존재하지 않는 게시글입니다.", LocalDateTime.now(),HttpStatus.NOT_FOUND,requestURI)

        val comment = Comment(
                content = content,
                user = user,
                article = article
        )
        commentRepository.save(comment)

        article.addComment(comment)
        user.addComment(comment)

        return CommentResponse(
                commentId = comment.id,
                email = user.email,
                content = content
        )
    }


    @Transactional(readOnly = false)
    fun edit(articleId: Long,commentId:Long,user: User,content: String,requestURI: String):CommentResponse{
        val comment = getComment(articleId, commentId, requestURI)
        isValid(comment,user,requestURI)
        comment.edit(content)
        return CommentResponse(
                commentId = commentId,
                email = user.email,
                content = content
        )
    }

    @Transactional(readOnly = false)
    fun remove(articleId: Long,commentId:Long,user: User,requestURI: String){
        val comment = getComment(articleId, commentId, requestURI)
        isValid(comment,user,requestURI)
        commentRepository.delete(comment)
    }


    fun getComment(articleId: Long,commentId: Long,requestURI: String):Comment{
        val article = articleRepository.findByIdOrNull(articleId) ?: throw BlogException("존재하지 않는 게시글입니다.", LocalDateTime.now(),HttpStatus.NOT_FOUND,requestURI)
        return commentRepository.findByIdAndArticle(commentId, article)
                ?: throw BlogException("존재하지 않는 댓글입니다.", LocalDateTime.now(), HttpStatus.NOT_FOUND, requestURI)
    }

    fun isValid(comment: Comment,user: User,requestURI: String){
        if(!comment.user.isSame(user))
            throw BlogException("접근 권한이 없습니다", LocalDateTime.now(),HttpStatus.FORBIDDEN,requestURI)
    }

}