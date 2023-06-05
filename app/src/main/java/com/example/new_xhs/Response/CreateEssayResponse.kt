package com.example.new_xhs.Response

import com.example.new_xhs.EssayEntity

data class CreateEssayResponse(
    val code: Int,
    val msg: String,
    val essayEntity: EssayEntity
)