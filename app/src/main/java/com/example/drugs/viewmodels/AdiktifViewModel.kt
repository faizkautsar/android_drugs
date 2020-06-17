package com.example.drugs.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drugs.models.Adiktif
import com.example.drugs.webservices.ApiClient
import com.example.drugs.webservices.SingleLiveEvent
import com.example.drugs.webservices.WrappedListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdiktifViewModel : ViewModel() {
    private val state: SingleLiveEvent<AdiktifState> = SingleLiveEvent()
    private val api = ApiClient.instance()
    private val adiktifs = MutableLiveData<List<Adiktif>>()

    private fun setLoading() {
        state.value = AdiktifState.Loading(true)
    }

    private fun hideLoading() {
        state.value = AdiktifState.Loading(false)
    }

    private fun toast(m: String) {
        state.value = AdiktifState.ShowToast(m)
    }

    fun fetchAdiktif() {
        setLoading()
        api.getAdiktif().enqueue(object : Callback<WrappedListResponse<Adiktif>> {
            override fun onFailure(
                call: Call<WrappedListResponse<Adiktif>>,
                t: Throwable
            ) {
                println(t.message)
                hideLoading()
                toast(t.message.toString())
            }

            override fun onResponse(
                call: Call<WrappedListResponse<Adiktif>>,
                response: Response<WrappedListResponse<Adiktif>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    adiktifs.postValue(body?.data)
                    println(body!!.data)
                }
                hideLoading()
            }
        })
    }


    fun listenToState() = state
    fun listenToAdiktif() = adiktifs

}
sealed class AdiktifState {
    data class Loading(var state: Boolean) : AdiktifState()
    data class ShowToast(var message: String) : AdiktifState()
}