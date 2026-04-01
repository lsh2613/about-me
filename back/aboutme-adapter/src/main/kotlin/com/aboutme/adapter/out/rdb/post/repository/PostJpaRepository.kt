package com.aboutme.adapter.out.rdb.post.repository

import com.aboutme.adapter.out.rdb.post.entity.PostEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface PostJpaRepository : JpaRepository<PostEntity, Long> {
    @Query("SELECT * FROM post", nativeQuery = true)
    fun findAllWithDeleted(): List<PostEntity>

    @Query("DELETE FROM post WHERE id = :postId", nativeQuery = true)
    fun hardDelete(postId: Long)

    @Modifying
    @Query("UPDATE post SET deleted_at = NULL WHERE id = :postId", nativeQuery = true)
    fun restore(postId: Long)
}
