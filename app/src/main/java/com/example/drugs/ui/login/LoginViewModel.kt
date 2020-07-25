package com.example.drugs.ui.login

import androidx.lifecycle.ViewModel
import com.example.drugs.models.User
import com.example.drugs.repositories.FirebaseRepository
import com.example.drugs.repositories.UserRepository
import com.example.drugs.utils.SingleResponse
import com.example.drugs.webservices.Constants
import com.example.drugs.webservices.SingleLiveEvent

class LoginViewModel(
    private val firebaseRepo: FirebaseRepository,
    private val userRepo: UserRepository
) : ViewModel(){
    private val state: SingleLiveEvent<LoginState> = SingleLiveEvent()

    private fun setLoading(){ state.value = LoginState.Loading(true) }
    private fun hideLoading(){ state.value = LoginState.Loading(false) }
    private fun success(param: String){ state.value = LoginState.Success(param) }
    private fun alert(message: String){ state.value = LoginState.Alert(message) }
    private fun reset() { state.value = LoginState.Reset }


    private fun login(email: String, password: String, fcmToken: String) {
            setLoading()
            userRepo.login(email, password, fcmToken,  object : SingleResponse<User> {
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

    fun Validate(email : String, pass : String) : Boolean {
        reset()

        if (email.isEmpty()) {
            state.value = LoginState.Validate(email = "Email tidak boleh kosong!")
            return false
        } else {
            if (!Constants.isValidEmail(email)) {
                state.value = LoginState.Validate(email = "Email tidak valid")
                return false
            }
        }

        if (pass.isEmpty()) {
            state.value = LoginState.Validate(pass = "Password tidak boleh kosong!")
            return false
        } else {
            val password = pass.length < 6
            if (password) {
                state.value = LoginState.Validate(pass = "password minimal 6 karakter")
                return false
            }
        }
        return true
    }

    fun getFirebaseToken(email: String, password: String){
        setLoading()
        firebaseRepo.getToken(object: SingleResponse<String>{
            override fun onSuccess(data: String?) {
                hideLoading()
                firebaseRepo.getToken(object: SingleResponse<String>{
                    override fun onSuccess(data: String?) {
                        hideLoading()
                        data?.let { fcmToken ->
                            login(email, password, fcmToken)
                        }
                    }

                    override fun onFailure(err: Error) {
                        hideLoading()
                        alert(err.message.toString())
                    }
                })
            }

            override fun onFailure(err: Error) {
                hideLoading()
                err.message?.let { alert("Tidak dapat membuat akun. Coba lagi nanti") }
            }
        })
    }


    fun getState() = state
}

sealed class LoginState {
    data class Alert(val message : String) : LoginState()
    data class Success(val token : String) : LoginState()
    data class Loading(val state: Boolean) : LoginState()
    data class ShowToast(val message: String) : LoginState()
    data class Validate(
        var email : String? = null,
        var pass : String? = null
        ) : LoginState()
    object Reset :LoginState()
}