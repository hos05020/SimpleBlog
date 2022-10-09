package com.example.BackendTask.repository

import com.example.BackendTask.domain.Article
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<Article,Long>{

}