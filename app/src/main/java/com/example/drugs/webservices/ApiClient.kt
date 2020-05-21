package com.example.drugs.webservices

import com.example.drugs.models.Rehab
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        const val ENDPOINT = "https://no-drugs.herokuapp.com/"
        private val okHttp = OkHttpClient.Builder().apply {
            readTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
        }.build()

        private var retrofit : Retrofit? = null

        private fun getClient() : Retrofit {
            return if(retrofit == null){
                retrofit = Retrofit.Builder().apply {
                    baseUrl(ENDPOINT)
                    addConverterFactory(GsonConverterFactory.create())
                    client(okHttp)
                }.build()
                retrofit!!
            }else{ retrofit!! }
        }

        fun instance() = getClient().create(ApiService::class.java)
    }
}

interface ApiService {
    @GET("api/rehabilitasi")
    fun getRehab() : Call<WrappedListResponse<Rehab>>
}

data class WrappedResponse<T>(
    @SerializedName("message") var message : String,
    @SerializedName("status") var status : Boolean,
    @SerializedName("data") var data : T
)

data class WrappedListResponse<T>(
    @SerializedName("message") var message : String,
    @SerializedName("status") var status : Boolean,
    @SerializedName("data") var data : List<T> = mutableListOf()
)