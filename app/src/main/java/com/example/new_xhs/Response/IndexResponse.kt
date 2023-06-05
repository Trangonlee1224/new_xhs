package com.example.new_xhs.Response

import com.example.new_xhs.Categories
import com.example.new_xhs.EssayEntity

data class IndexResponse(
    val code: Int,
    val msg: String,
    val data: IndexData
){
    data class IndexData(
        val categoriesList: List<Categories>,
        val essayEntities: List<EssayEntity>
    )
}
