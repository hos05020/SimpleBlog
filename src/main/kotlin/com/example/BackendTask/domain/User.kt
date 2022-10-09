package com.example.BackendTask.domain

import javax.persistence.*

@Entity
class User(

        val email: String,

        val password: String,

        val username: String,

        @OneToMany(mappedBy = "user", orphanRemoval = true)
        private var articles: MutableList<Article> = mutableListOf(),

        @OneToMany(mappedBy = "user", orphanRemoval = true)
        private var comments: MutableList<Comment> = mutableListOf()

) : BaseEntity() {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private val id: Long? = null


    fun isSame(user: User): Boolean {
        return this.id == user.id
    }

    fun addArticle(article: Article) {
        articles.add(article)
    }

    fun addComment(comment: Comment) {
        comments.add(comment)
    }


}