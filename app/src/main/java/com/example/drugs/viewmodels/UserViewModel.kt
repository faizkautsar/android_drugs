package com.example.drugs.viewmodels

import androidx.lifecycle.ViewModel
import com.example.drugs.models.User
import com.example.drugs.webservices.ApiClient
import com.example.drugs.webservices.SingleLiveEvent
import com.example.drugs.webservices.WrappedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel(){
    private var state : SingleLiveEvent<UserState> = SingleLiveEvent()
    private var api  = ApiClient.instance()

    private fun setLoading() { state.value = UserState.IsLoading(true) }
    private fun hideLoading() { state.value = UserState.IsLoading(false) }
    private fun toast(message: String) { state.value = UserState.ShowToast(message) }

    /*fun validate(nama: String?, email: String, pass: String, no_telp: String?, alamat: String?
    desa: String?, kecamatan: String?, kode_pos: String?) : Boolean {
        state.value = UserState.Reset

        if (nama.length < 5){
            state.value = UserState.Validate(nama = "Nama Minimal 5 Karakter")
        }
        if (nama.isNullOrEmpty()){
            state.value = UserState.Validate(nama = "nama tidak boleh kosong")
        }

        if (email.isEmpty()){
            state.value = UserState.Validate(email = "email tidak boleh kosong")
        }

        if (pass.isEmpty()){
            state.value = UserState.Validate(pass = "password tidak boleh kosong")
        }

        if (no_telp!!.isEmpty()){
            state.value = UserState.Validate(no_telp = "no hp tidak boleh kosong")
        }

        if (alamat!!.isEmpty()){
            state.value = UserState.Validate(alamat = "alamat tidak boleh kosong")
        }
        if (desa!!.isEmpty()){
            state.value = UserState.Validate(desa = "desa tidak boleh kosong")
        }

        if (kecamatan.isEmpty()){
            state.value = UserState.Validate(kecamatan = "kecamatan tidak boleh kosong")
        }

        if (kode_pos.isEmpty()){
            state.value = UserState.Validate(kode_pos = "kode pos tidak boleh kosong")
        }
    }
    */
    fun register(nama : String, email : String, pass : String, no_telp : String, alamat : String,
    desa : String, kecamatan : String, kode_pos : String){
        setLoading()
        api.registrasi(nama, email, pass, no_telp, alamat, desa, kecamatan, kode_pos)
            .enqueue(object : Callback<WrappedResponse<User>>{
                override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                    println(t.message)
                    hideLoading()
                }

                override fun onResponse(call: Call<WrappedResponse<User>>, response: Response<WrappedResponse<User>>) {
                    if (response.isSuccessful){
                        val body = response.body()
                        if (body?.status!!){
                            toast("berhasil")
                            state.value = UserState.Success
                        }else{
                            println("b : ${body.message}")
                        }
                    }else{
                        println("r : ${response.message()}")
                    }
                    hideLoading()
                }

            })
    }

    fun login(email: String, pass: String){
        setLoading()
        api.login(email, pass).enqueue(object : Callback<WrappedResponse<User>>{
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                println(t.message)
                hideLoading()
            }

            override fun onResponse(call: Call<WrappedResponse<User>>, response: Response<WrappedResponse<User>>) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body?.status!!){
                        state.value = UserState.SuccessLogin(body.data.token!!)
                    }else{
                        println("b : ${body.message}")
                    }
                }else{
                    println("r : ${response.message()}")
                }
                hideLoading()
            }

        })
    }

    fun listenToState() = state

}

sealed class UserState{
    data class IsLoading(var state : Boolean = false) : UserState()
    data class ShowToast(var message : String) : UserState()
    object Success : UserState()
    object Reset : UserState()
    data class SuccessLogin(var token : String) : UserState()
    data class Validate(
        var name : String? = null,
        val email : String? = null,
        var pass : String? = null,
        var no_telp: String? = null,
        var alamat : String? = null,
        var desa : String? = null,
        var kecamatan: String? = null,
        var kode_pos: String? = null
    ) : UserState()
}