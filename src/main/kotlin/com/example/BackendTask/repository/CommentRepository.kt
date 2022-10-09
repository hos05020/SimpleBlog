package com.example.BackendTask.repository

import com.example.BackendTask.domain.Article
import com.example.BackendTask.domain.Comment
import org.hibernate.sql.Select
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*


interface CommentRepository : JpaRepository<Comment,Long>{
    @EntityGraph(attributePaths = ["user", "article"], type = EntityGraph.EntityGraphType.LOAD)
    fun findByIdAndArticle(id:Long,article: Article):Comment?
}