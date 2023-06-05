package com.example.new_xhs.Service

import com.example.new_xhs.EssayEntity
import com.example.new_xhs.Request.*
import com.example.new_xhs.Response.*
import com.example.new_xhs.Tags
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService{
    @POST("user/login")
    fun loginUser(@Body request:LoginRequest): Call<LoginResponse>

    @POST("user/logout")
    fun logout(@Body request: LogoutRequest): Call<LogoutResponse>

    @POST("user/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("essay/create")
    fun createEssay(@Body request:CreateEssayRequest): Call<CreateEssayResponse>

    @POST("essay/delete")
    fun deleteEssay(@Body request: DeleteEssayRequest): Call<DeleteEssayResponse>

    @POST("essay/tag/create")
    fun createTag(@Body request: CreateTagRequest): Call<CreateTagResponse>

    @POST("essay/update")
    fun updateEssay(@Body request: EssayEntity): Call<Unit>

    @POST("essay/tag/update")
    fun updateTag(@Body request:Tags): Call<Unit>

    @POST("essay/tag/delete")
    fun deleteTag(@Body request: Tags): Call<Unit>

    @GET("index")
    fun getIndex():Call<IndexResponse>

    @GET("index")
    fun getIndex(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Call<IndexResponse>

    @GET("essay/user/category")
    fun getEssayWithCategoryAndUser(
        @Query("user_id") userId: Int,
        @Query("username") username: String,
        @Query("category_id") categoryId: Int,
        @Query("category_name") categoryName: String
    ): Call<EssaysResponse>

    @GET("essay/category")
    fun getEssaysWithCategory(
        @Query("category_id") categoryId: Int,
        @Query("category_name") categoryName: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Call<EssaysResponse>

    @GET("essay/tag")
    fun getEssayWithTag(
        @Query("tag_id") tagId: Int,
        @Query("tag_name") tagName: String
    ): Call<EssaysResponse>

    @GET("essay/user")
    fun getEssayWithUser(
        @Query("user_id") userId: Int,
        @Query("username") username: String
    ): Call<EssaysResponse>

    @GET("tag")
    fun getTagsWithUser(
        @Query("user_id") userId: Int,
        @Query("username") username: String
    ): Call<TagResponse>


}