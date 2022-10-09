package com.example.BackendTask.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    protected var createdAt : LocalDateTime = LocalDateTime.MIN

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    protected var updatedAt : LocalDateTime = LocalDateTime.MIN

}