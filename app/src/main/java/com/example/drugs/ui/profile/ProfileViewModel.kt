package com.example.drugs.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drugs.models.User
import com.example.drugs.repositories.UserRepository
import com.example.drugs.utils.SingleResponse
import com.example.drugs.webservices.SingleLiveEvent

class ProfileViewModel (private val userRepository: UserRepository) : ViewModel(){
    private val state : SingleLiveEvent<ProfileState> = SingleLiveEvent()
    private val user  = MutableLiveData<User>()
    private val imagePath = MutableLiveData<String>("")

    private fun setLoading(){ state.value = ProfileState.Loading(true) }
    private fun hideLoading(){ state.value = ProfileState.Loading(false) }
    private fun toast(message: String){ state.value = ProfileState.ShowToaast(message) }
    private fun success() { state.value = ProfileState.Success }
    private fun update(){state.value = ProfileState.Update}

    fun setImagePath(imgPath : String){
        imagePath.value = imgPath
    }


    fun validate(nama: String,  no_telp : String, jalan: String, desa: String, kecamatan : String,kota: String) : Boolean{
        if (nama.isEmpty()){
            state.value = ProfileState.Validate(nama = "Nama tidak boleh kosong")
            return false
        }
        if (nama.length < 4) {
            state.value = ProfileState.Validate(nama = "Nama minimal 4 karakter")
            return false
        }
        if(no_telp.isEmpty()){
            state.value = ProfileState.Validate(no_telp = "Nomer telepon tidak boleh kosong")
            return false
        }

        if(!(no_telp.length >= 11 && no_telp.length <= 13)){
            state.value = ProfileState.Validate(no_telp = "Nomer telepon tidak valid")
            return false
        }

        if(jalan.isEmpty()){
            state.value = ProfileState.Validate(jalan = "Jalan tidak boleh kosong")
            return false
        }

        if(jalan.length < 10){
            state.value = ProfileState.Validate(jalan = "Nama jalan minimal 10 karakter")
            return false
        }

        if(desa.isEmpty()){
            state.value = ProfileState.Validate(desa = "Desa tidak boleh kosong")
            return false
        }
        if (desa.length <5){
                state.value = ProfileState.Validate(desa = "Nama minimal 5 karakter")
                return false
            }


        if(kecamatan.isEmpty()){
            state.value = ProfileState.Validate(kecamatan = "Kecamatan tidak boleh kosong")
            return false
        }
        if (kecamatan.length <6){
                state.value = ProfileState.Validate(kecamatan =  "Kecamatan minimal 6 karakter")
                return false
        }

        if(kota.isEmpty()){
            state.value = ProfileState.Validate(kota = "Kota tidak boleh kosong")
            return false
        }
            if(kota.length < 4){
                state.value = ProfileState.Validate(kota =  "Kota minimal 4 karakter")
                return false
            }


        return true
    }
    fun Update(token: String, data: User){
        setLoading()
        userRepository.update(token, data, object : SingleResponse<User>{
            override fun onSuccess(data: User?) {
                hideLoading()
                data?.let { update() }
            }
            override fun onFailure(err: Error) {
                hideLoading()
                toast(err.message.toString())
            }
        })
    }

    fun uploadImage(token: String, path: String){
        setLoading()
        userRepository.uploadFoto(token, path, object : SingleResponse<User>{
            override fun onSuccess(data: User?) {
                hideLoading()
                println("success")
                data?.let { success() }
            }
            override fun onFailure(err: Error) {
                hideLoading()
                err.message?.let { toast(it) }

            }
        })
    }


    fun profile(token : String){
        setLoading()
        userRepository.profile(token, object : SingleResponse<User>{
            override fun onSuccess(data: User?) {
                hideLoading()
                data?.let {
                    println("user : $it")
                    user.postValue(it)
                }
            }

            override fun onFailure(err: Error) {
                hideLoading()
                toast(err.message.toString())
            }
        })
    }

    fun lisrenToImagePath() = imagePath
    fun listenToState() = state
    fun listenToUser() = user
}

sealed class ProfileState{
    data class Validate(val nama: String? = null, val no_telp : String?= null, val jalan: String? = null,
                        val desa: String? = null, val kecamatan : String? = null, val kota: String? = null) : ProfileState()
    object Update:ProfileState()
    object Reset : ProfileState()
    data class Alert(val message: String) : ProfileState()
    data class uploadImage(val imagePath : String) : ProfileState()
    object Success : ProfileState()
    object SuccessUpdate : ProfileState()
    data class Loading(var state : Boolean = false) : ProfileState()
    data class ShowToaast(var message : String) : ProfileState()
}
