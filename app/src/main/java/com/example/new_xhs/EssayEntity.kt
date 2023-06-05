package com.example.new_xhs
import com.example.new_xhs.UserEntity
import com.example.new_xhs.Categories

data class EssayEntity(
    val passage_id: Int,
    val user: UserEntity,
    val content: String,
    val abs: String,
    val title: String,
    val published: Int,
    val category: Categories,
    val tags: List<TagEntity>,
    val createTime: String,
    val updateTime: String,
    val coverPhoto: String
)