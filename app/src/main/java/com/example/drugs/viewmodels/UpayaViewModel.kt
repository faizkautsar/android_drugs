package com.example.drugs.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drugs.models.Upaya
import com.example.drugs.webservices.ApiClient
import com.example.drugs.webservices.SingleLiveEvent
import com.example.drugs.webservices.WrappedListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpayaViewModel : ViewModel() {
    private val state: SingleLiveEvent<UpayaState> = SingleLiveEvent()
    private val api = ApiClient.instance()
    private val upayas = MutableLiveData<List<Upaya>>()

    private fun setLoading() {
        state.value = UpayaState.Loading(true)
    }

    private fun hideLoading() {
        state.value = UpayaState.Loading(false)
    }

    private fun toast(m: String) {
        state.value = UpayaState.ShowToast(m)
    }

    fun fetchUpaya() {
        setLoading()
        api.getUpaya().enqueue(object : Callback<WrappedListResponse<Upaya>> {
            override fun onFailure(call: retrofit2.Call<WrappedListResponse<Upaya>>, t: Throwable) {
                println(t.message)
                hideLoading()
                toast(t.message.toString())
            }

            override fun onResponse(
                call: retrofit2.Call<WrappedListResponse<Upaya>>,
                response: retrofit2.Response<WrappedListResponse<Upaya>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    upayas.postValue(body?.data)
                }
                hideLoading()
            }

        })
    }
    fun listenToState() = state
    fun listenToUpaya() = upayas

}
    sealed class UpayaState {
        data class Loading(var state: Boolean) : UpayaState()
        data class ShowToast(var message: String) : UpayaState()
    }
