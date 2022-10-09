package com.example.BackendTask.domain

import javax.persistence.*

@Entity
class Article(

        title: String,

        content: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        val user: User,

        @OneToMany(mappedBy = "article", orphanRemoval = true)
        val comments: MutableList<Comment> = mutableListOf(),

        ) : BaseEntity() {

    @Id
    @GeneratedValue
    @Column(name = "article_id")
    val id: Long? = null

    var title = title
        protected set

    var content = content
        protected set


    fun editArticle(title: String, content: String) {
        this.title = title
        this.content = content
    }


    fun addComment(comment: Comment) {
        comments.add(comment)
    }


}