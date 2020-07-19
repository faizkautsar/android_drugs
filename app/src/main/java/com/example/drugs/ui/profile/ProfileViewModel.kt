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

    fun setImagePath(imgPath : String){
        imagePath.value = imgPath
    }

    fun uploadImage(token: String, path: String){
        setLoading()
        userRepository.uploadFoto(token, path, object : SingleResponse<User>{
            override fun onSuccess(data: User?) {
                hideLoading()
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
                data?.let { user.postValue(it) }
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
    data class UploadImage(val imagePath : String) : ProfileState()
    object Success : ProfileState()
    data class Loading(var state : Boolean = false) : ProfileState()
    data class ShowToaast(var message : String) : ProfileState()
}