package com.example.drugs.ui.register

import androidx.lifecycle.ViewModel
import com.example.drugs.models.User
import com.example.drugs.repositories.FirebaseRepository
import com.example.drugs.repositories.UserRepository
import com.example.drugs.utils.SingleResponse
import com.example.drugs.webservices.Constants
import com.example.drugs.webservices.SingleLiveEvent

class RegisterViewModel (
    private val userRepository: UserRepository,
    private val firebaseRepo: FirebaseRepository
) : ViewModel(){
    private val state: SingleLiveEvent<RegisterState> = SingleLiveEvent()

    private fun setLoading(){ state.value = RegisterState.Loading(true) }
    private fun hideLoading(){ state.value = RegisterState.Loading(false) }
    private fun alert(message: String){ state.value = RegisterState.Alert(message) }
    private fun success(param : String){ state.value = RegisterState.Success(param) }
    private fun reset() { state.value = RegisterState.Reset }

    fun Validate(nama : String, email : String, pass : String, rePass: String, no_telp: String, jalan : String, desa: String,
    kecamatan: String, kota: String) : Boolean {
        reset()
        if (nama.isEmpty()) {
            state.value = RegisterState.Validate(nama = "Nama tidak boleh kosong!")
            return false
        }
        if (nama.length < 4) {
            state.value = RegisterState.Validate(nama = "Nama minimal 4 karakter")
            return false
        }

        if (email.isEmpty()) {
            state.value = RegisterState.Validate(email = "Email tidak boleh kosong!")
            return false
        }
        if (!Constants.isValidEmail(email)) {
            state.value = RegisterState.Validate(email = "Email tidak valid")
            return false
        }

        if (pass.isEmpty()) {
            state.value = RegisterState.Validate(pass = "Password tidak boleh kosong!")
            return false
        }
        if (pass.length < 6) {
            state.value = RegisterState.Validate(pass = "password minimal 6 karakter")
            return false
        }

        if (rePass.isEmpty()) {
            state.value = RegisterState.Validate(rePass = "Repassword tidak boleh kosong!")
            return false
        }
        if (rePass.length < 6) {
            state.value = RegisterState.Validate(rePass = "Repassword minimal 6 karakter")
            return false
        }

        if (no_telp.isEmpty()) {
            state.value = RegisterState.Validate(no_telp = "Nomer telepon tidak boleh kosong!")
            return false
        }
        if (!(no_telp.length >= 10 && no_telp.length <= 13)) {
            state.value = RegisterState.Validate(no_telp = "Nomer telepon tidak valid")
            return false
        }
        if (jalan.isEmpty()) {
            state.value = RegisterState.Validate(jalan = "Nama jalan tidak boleh kosong!")
            return false
        }

        if (jalan.length < 8) {
            state.value = RegisterState.Validate(jalan = "Nama jalan minimal 8 karakter")
            return false
        }

        if (desa.isEmpty()) {
            state.value = RegisterState.Validate(desa = "Desa/Dusun tidak boleh kosong!")
            return false
        }
        if (desa.length < 5) {
            state.value = RegisterState.Validate(desa = "Nama  desa minimal 6 karakter")
            return false
        }

        if (kecamatan.isEmpty()) {
            state.value = RegisterState.Validate(kecamatan = "Kecamatan tidak boleh kosong!")
            return false
        }
        if (kecamatan.length < 5) {
            state.value = RegisterState.Validate(kecamatan = "Nama kecamatan minimal 5 karakter")
            return false
        }
        if (kota.isEmpty()) {
            state.value = RegisterState.Validate(kota = "Kota/Kabupaten tidak boleh kosong!")
            return false
        }
        if (kota.length < 4) {
            state.value = RegisterState.Validate(kota = "Nama kota minimal 4 karakter")
            return false
        }

            return true
        }

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
    data class Validate(
        var nama : String? = null,
        var email : String? = null,
        var pass : String? = null,
        var rePass : String? = null,
        var no_telp : String? = null,
        var jalan : String? = null,
        var desa : String? = null,
        var kecamatan : String? = null,
        var kota : String? = null
    ) : RegisterState()
    object Reset : RegisterState()
}