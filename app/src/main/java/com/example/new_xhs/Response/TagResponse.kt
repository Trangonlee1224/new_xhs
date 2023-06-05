package com.example.new_xhs.Response

import com.example.new_xhs.TagEntity

data class TagResponse(
    val code: Int,
    val msg: String,
    val data: Data
){
    data class Data(
        val tagEntities: List<TagEntity>
    )
}
