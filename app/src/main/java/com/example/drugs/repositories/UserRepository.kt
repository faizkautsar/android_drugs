package com.example.drugs.repositories

import com.example.drugs.models.User
import com.example.drugs.utils.SingleResponse
import com.example.drugs.webservices.ApiService
import com.example.drugs.webservices.WrappedResponse
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface UserContract {
    fun register(user: User, listener: SingleResponse<User>)
    fun login(email: String, password: String, listener: SingleResponse<User>)
}

class UserRepository (private val api: ApiService) : UserContract {
    override fun register(user: User, listener: SingleResponse<User>) {
        val requestBody = Gson().toJson(user)
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody)
        api.registrasi(body).enqueue(object: Callback<WrappedResponse<User>>{
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) = listener.onFailure(Error(t.message))

            override fun onResponse(call: Call<WrappedResponse<User>>, response: Response<WrappedResponse<User>>) {
                when{
                    response.isSuccessful -> {
                        val b = response.body()
                        if (b!!.status) listener.onSuccess(b.data) else listener.onFailure(Error(b.message))
                    }
                    else -> listener.onFailure(Error(response.message()))
                }
            }
        })
    }

    override fun login(email: String, password: String, listener: SingleResponse<User>) {
        api.login(email, password).enqueue(object : Callback<WrappedResponse<User>>{
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) = listener.onFailure(Error(t.message))

            override fun onResponse(call: Call<WrappedResponse<User>>, response: Response<WrappedResponse<User>>) {
                when{
                    response.isSuccessful -> {
                        val b = response.body()
                        if(b!!.status) listener.onSuccess(b.data) else listener.onFailure(Error(b.message))
                    }
                    else -> listener.onFailure(Error(response.message()))
                }
            }
        })
    }

}