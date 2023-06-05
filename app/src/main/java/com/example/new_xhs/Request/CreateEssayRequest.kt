package com.example.new_xhs.Request

import com.example.new_xhs.Categories
import com.example.new_xhs.TagEntity
import com.example.new_xhs.UserEntity

data class CreateEssayRequest(
    val passage_id: Int?,
    val user: UserEntity,
    val content: String,
    val abs: String?,
    var title: String,
    val published: Int?,
    val category: Categories,
    val tags: List<TagEntity>,
    val createTime: String?,
    val updateTime: String?,
    val cover_photo: String?
)