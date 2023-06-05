package com.example.new_xhs

import java.sql.Time

data class Users(
    val user_id: Int,
    val username: String? = null,
    val password: String? = null,
    val email: String? = null,
    val avatar: String? = null,
    val sex: String? = null,
    val createTime: Time? = null,
    val updateTime: Time? = null
)