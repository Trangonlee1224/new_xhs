package com.example.new_xhs.Response

import com.example.new_xhs.EssayEntity

data class EssaysResponse(
    val code: Int,
    val msg: String,
    val data: EssaysData
){
    data class EssaysData(
        val essayEntities: List<EssayEntity>
    )
}

