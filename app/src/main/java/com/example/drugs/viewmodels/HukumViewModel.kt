package com.example.drugs.viewmodels

import android.telecom.Call
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drugs.models.Hukum
import com.example.drugs.webservices.ApiClient
import com.example.drugs.webservices.SingleLiveEvent
import com.example.drugs.webservices.WrappedListResponse
import okhttp3.Response
import retrofit2.Callback

class HukumViewModel : ViewModel() {
private val state: SingleLiveEvent<HukumState> = SingleLiveEvent()
private val api = ApiClient.instance()
private val hukums = MutableLiveData<List<Hukum>>()

private fun setLoading() {
    state.value = HukumState.Loading(true)
}

private fun hideLoading() {
    state.value = HukumState.Loading(false)
}

private fun toast(m: String) {
    state.value = HukumState.ShowToast(m)
}

fun fetchHukum() {
    setLoading()
    api.getHukum().enqueue(object : Callback<WrappedListResponse<Hukum>>{
        override fun onFailure(call: retrofit2.Call<WrappedListResponse<Hukum>>, t: Throwable) {
            println(t.message)
            hideLoading()
            toast(t.message.toString())
        }

        override fun onResponse(call: retrofit2.Call<WrappedListResponse<Hukum>>, response: retrofit2.Response<WrappedListResponse<Hukum>>) {
            if (response.isSuccessful) {
                val body = response.body()
                hukums.postValue(body?.data)
            }
            hideLoading()
        }

    })
}


fun listenToState() = state
fun listenToHukum() = hukums

}
sealed class HukumState {
    data class Loading(var state: Boolean) : HukumState()
    data class ShowToast(var message: String) : HukumState()
}