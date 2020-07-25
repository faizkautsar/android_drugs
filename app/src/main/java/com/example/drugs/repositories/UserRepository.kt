package com.example.drugs.repositories

import com.example.drugs.models.User
import com.example.drugs.utils.SingleResponse
import com.example.drugs.webservices.ApiService
import com.example.drugs.webservices.WrappedResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

interface UserContract {
    fun uploadFoto(token: String, imagePath : String, listener: SingleResponse<User>)
    fun register(user: User, listener: SingleResponse<User>)
    fun login(email: String, password: String, fcmToken: String, listener: SingleResponse<User>)
    fun profile(token : String, listener: SingleResponse<User>)
    fun update(token: String, user : User, listener: SingleResponse<User>)
}

class UserRepository (private val api: ApiService) : UserContract {

    override fun uploadFoto(token: String, imagePath: String, listener: SingleResponse<User>) {
        val f = File(imagePath)
        val requestBody = RequestBody.create(MediaType.parse("image/*"), f)
        val image = MultipartBody.Part.createFormData("foto", f.name, requestBody)
        api.foto(token, image).enqueue(object : Callback<WrappedResponse<User>>{
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) = listener.onFailure(Error(t.message.toString()))

            override fun onResponse(call: Call<WrappedResponse<User>>, response: Response<WrappedResponse<User>>) {
                when{
                    response.isSuccessful -> listener.onSuccess(response.body()!!.data)
                    else -> listener.onFailure(Error(response.message()))
                }
            }
        })
    }

    override fun register(user: User, listener: SingleResponse<User>) {
        val g = GsonBuilder().create()
        val requestBody = g.toJson(user)
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody)
        api.registrasi(body).enqueue(object: Callback<WrappedResponse<User>>{
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) = listener.onFailure(Error(t.message))

            override fun onResponse(call: Call<WrappedResponse<User>>, response: Response<WrappedResponse<User>>) {
                when{
                    response.isSuccessful -> {
                        val b = response.body()
                        if (b!!.status)
                            listener.onSuccess(b.data)
                        else
                            listener.onFailure(Error("Gagal saat register. Mungkin email atau nomor telepon sudah pernah didaftarkan"))
                    }
                    else -> listener.onFailure(Error(response.message()))
                }
            }
        })
    }

    override fun login(email: String, password: String, fcmToken: String, listener: SingleResponse<User>) {
        api.login(email, password, fcmToken).enqueue(object : Callback<WrappedResponse<User>>{
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

    override fun profile(token: String, listener: SingleResponse<User>) {
        api.profile(token).enqueue(object :Callback<WrappedResponse<User>>{
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                listener.onFailure(Error(t.message))
            }

            override fun onResponse(
                call: Call<WrappedResponse<User>>,
                response: Response<WrappedResponse<User>>
            ) {
                when{
                    response.isSuccessful -> listener.onSuccess(response.body()!!.data)
                    else -> listener.onFailure(Error(response.message()))
                }
            }

        })
    }

    override fun update(token: String, user: User, listener: SingleResponse<User>) {
        val g = GsonBuilder().create()
        val requestBody = g.toJson(user)
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody)
        api.update(token, body).enqueue(object : Callback<WrappedResponse<User>>{
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                listener.onFailure(Error(t.message))
            }

            override fun onResponse(
                call: Call<WrappedResponse<User>>,
                response: Response<WrappedResponse<User>>
            ) {
                when{
                    response.isSuccessful -> {
                        val b = response.body()
                        if (b?.status!!){
                            listener.onSuccess(b.data)
                        }else{
                            listener.onFailure(Error(b.message))
                        }
                    }
                    !response.isSuccessful -> listener.onFailure(Error(response.message()))
                }
            }
        })
    }
}