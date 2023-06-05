package com.example.new_xhs.Response

import com.example.new_xhs.Tags

data class CreateTagResponse(
    val code: Int,
    val msg: String,
    val data: List<Tags>
)