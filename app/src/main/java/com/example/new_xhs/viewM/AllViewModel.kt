package com.example.new_xhs.viewM

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.new_xhs.EssayEntity
import com.example.new_xhs.Network.ApiClient
import com.example.new_xhs.Network.RetrofitClient
import com.example.new_xhs.Request.CreateEssayRequest
import com.example.new_xhs.Request.DeleteEssayRequest
import com.example.new_xhs.Request.LoginRequest
import com.example.new_xhs.Request.RegisterRequest
import com.example.new_xhs.Response.*
import com.example.new_xhs.Service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllViewModel:ViewModel() {

    private val apiService = ApiClient.apiService

    private val _publishResult = MutableLiveData<Boolean>()
    val publishResult: LiveData<Boolean> = _publishResult

    private val _deleteResult = MutableLiveData<Boolean>()
    val deleteResult: LiveData<Boolean> = _deleteResult

    private val _essayEntities = MutableLiveData<List<EssayEntity>>()
    val essayEntities: LiveData<List<EssayEntity>> get() = _essayEntities

    private val _loggedIn = MutableLiveData<Boolean>()
    val loggedIn: LiveData<Boolean> = _loggedIn

    private val _register_res = MutableLiveData<Int>()
    val register_res: LiveData<Int> = _register_res

    private val _username = MutableLiveData<String>()
    val Ausername: LiveData<String> = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _userid = MutableLiveData<Int>()
    val Auserid: LiveData<Int> = _userid



    fun AddEssay(createEssayRequest: CreateEssayRequest){
        val call = apiService.createEssay(createEssayRequest)

        call.enqueue(object : Callback<CreateEssayResponse> {
            override fun onResponse(call: Call<CreateEssayResponse>, response: Response<CreateEssayResponse>) {
                if (response.isSuccessful) {

                    val responseheader = response.headers()
                    Log.d("Addresponseheader:",responseheader.toString())
                    val cResponse = response.body()
                    if (cResponse != null) {
                        Log.d("ADDResponse:",cResponse.msg)
                    }
                    val publishResult = cResponse?.code == 200
                    _publishResult.value = publishResult
                } else {
                    _publishResult.value = false
                }
            }

            override fun onFailure(call: Call<CreateEssayResponse>, t: Throwable) {
                _publishResult.value = false
            }
        })
    }

    fun loginUser(username: String?, password: String?) {
        val loginRequest = LoginRequest(username, password)
        val call = apiService.loginUser(loginRequest)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginresponseheader = response.headers().toString()
                    Log.d("loginresponseheader:", loginresponseheader)
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        Log.d("LoginResponse:",loginResponse.msg)
                    }
                    val loggedIn = loginResponse?.code == 200
                    _loggedIn.value = loggedIn
                    _username.value = username
                    _password.value = password
                    _userid.value = response.body()?.data?.user_id
                } else {
                    _loggedIn.value = false
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

            }
        })
    }

    fun registerUser(username: String, password: String){
        val registerRequest = RegisterRequest(username, password)
        val call = apiService.register(registerRequest)

        call.enqueue(object: Callback<RegisterResponse>{
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>){
                if (response.isSuccessful){
                    val registerResponse = response.body()
                    val register_res = registerResponse?.code
                    _register_res.value = register_res
                }
                else{
                    _loggedIn.value = false
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable){
                _loggedIn.value = false
            }
        })
    }

    fun fetchArticles() {
        val call = apiService.getIndex()
        call.enqueue(object : Callback<IndexResponse> {
            override fun onResponse(call: Call<IndexResponse>, response: Response<IndexResponse>) {
                if (response.isSuccessful) {
                    val indexResponse = response.body()
                    if (indexResponse != null) {
                        Log.d("ArticleResponse:", indexResponse.msg)
                        Log.d("Response data:", indexResponse.data.toString())
                    }
                    _essayEntities.value = indexResponse?.data?.essayEntities
                    Log.d("看看这个回应如何:", _essayEntities.value.toString())
//                    essayEntities = _essayEntities
                } else {
                    Log.d("Error:", "网络请求失败")
                }
            }
            override fun onFailure(call: Call<IndexResponse>, t: Throwable) {
                Log.d("请求失败：","Not yet implemented")
            }
        })
    }


    fun deleteEssay(deleteessrequest: DeleteEssayRequest){
        val call = apiService.deleteEssay(deleteessrequest)
         call.enqueue(object: Callback<DeleteEssayResponse>{
             override fun onResponse(call: Call<DeleteEssayResponse>, response: Response<DeleteEssayResponse>){
                 if(response.isSuccessful){
                     val deleteresponse = response.body()
                     if(deleteresponse != null){
                         Log.d("DeleteResponse:", deleteresponse.msg)
                         _deleteResult.value = true
                     }
                     else{
                         Log.d("Error:", "网络请求失败")
                     }
                 }
                 else{
                     Log.d("Error:", "网络请求失败")
                 }
             }

             override fun onFailure(call: Call<DeleteEssayResponse>, t: Throwable) {
                 Log.d("请求失败：","Not yet implemented")
             }
         })
    }

}