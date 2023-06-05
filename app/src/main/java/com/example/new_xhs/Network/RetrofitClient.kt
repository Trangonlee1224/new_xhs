package com.example.new_xhs.Network

import android.app.backup.SharedPreferencesBackupHelper
import com.example.new_xhs.Service.ApiService
import com.google.android.gms.common.util.SharedPreferencesUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import okhttp3.JavaNetCookieJar
import okhttp3.CookieJar
import okhttp3.Cookie
import okhttp3.HttpUrl
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Call

object RetrofitClient {
    private const val BASE_URL = "http://10.62.174.114:8080"

    private var retrofit: Retrofit? = null
    private var sessionId: String? = null

    fun getClient(): Retrofit {
        if (retrofit == null) {
            val okHttpClient = OkHttpClient.Builder()
                .cookieJar(object : CookieJar {
                    private val cookieStore: MutableMap<String, MutableList<Cookie>> = HashMap()

                    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                        if (sessionId == null) {
                            // Save the sessionid cookie from the first response
                            for (cookie in cookies) {
                                if (cookie.name == "sessionid") {
                                    sessionId = cookie.value
                                    break
                                }
                            }
                        }
                        cookieStore[url.host] = cookies.toMutableList()
                    }

                    override fun loadForRequest(url: HttpUrl): List<Cookie> {
                        val cookies = cookieStore[url.host]
                        return cookies ?: ArrayList()
                    }
                })
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    fun getSessionId(): String? {
        return sessionId
    }
}
object ApiClient {
    private val retrofit = RetrofitClient.getClient()
    val apiService: ApiService = retrofit.create(ApiService::class.java)

    fun getSessionId(): String? {
        return RetrofitClient.getSessionId()
    }
}

