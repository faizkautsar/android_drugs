package com.example.drugs.ui.register

import androidx.lifecycle.ViewModel
import com.example.drugs.models.User
import com.example.drugs.repositories.UserRepository
import com.example.drugs.utils.SingleResponse
import com.example.drugs.webservices.SingleLiveEvent

class RegisterViewModel (private val userRepository: UserRepository) : ViewModel(){
    private val state: SingleLiveEvent<RegisterState> = SingleLiveEvent()

    private fun setLoading(){ state.value = RegisterState.Loading(true) }
    private fun hideLoading(){ state.value = RegisterState.Loading(false) }
    private fun alert(message: String){ state.value = RegisterState.Alert(message) }
    private fun success(param : String){ state.value = RegisterState.Success(param) }

    fun register(user: User){
        setLoading()
        userRepository.register(user, object : SingleResponse<User>{
            override fun onSuccess(data: User?) {
                hideLoading()
                data?.let { success(it.token.toString()) }
            }
            override fun onFailure(err: Error) {
                hideLoading()
                err.message?.let { alert(it) }
            }
        })
    }

    fun getState() = state
}

sealed class RegisterState {
    data class Success(val param: String): RegisterState()
    data class Loading(val state: Boolean) : RegisterState()
    data class Alert(val message: String) : RegisterState()
}