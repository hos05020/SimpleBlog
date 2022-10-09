package com.example.BackendTask.domain

import javax.persistence.*

@Entity
class Comment(


        content: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        val user: User,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "article_id")
        val article: Article


) : BaseEntity() {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    val id: Long? = null

    var content = content
        protected set

    fun edit(content: String) {
        this.content = content
    }

}