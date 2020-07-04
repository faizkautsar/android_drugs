package com.example.drugs.ui.register

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.drugs.R
import com.example.drugs.extensions.disabled
import com.example.drugs.extensions.enabled
import com.example.drugs.extensions.showInfoAlert
import com.example.drugs.models.User
import com.example.drugs.ui.login.LoginActivity
import com.example.drugs.viewmodels.UserState
import com.example.drugs.viewmodels.UserViewModel
import com.example.drugs.webservices.Constants
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        observe()
        reg()
    }

    private fun observe(){
        observeState()
    }

    private fun observeState() = registerViewModel.getState().observer(this, Observer { handleUI(it) })

    private fun handleUI(it : RegisterState){
        when(it){
            is RegisterState.Alert -> showInfoAlert(it.message)
            is RegisterState.Loading -> {
                if (it.state){
                    btn_register.disabled()
                }else{
                    btn_register.enabled()
                }
            }

            is RegisterState.Success-> {
                Constants.setToken(this@RegisterActivity, "Bearer ${it.param}")
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private fun reg(){
        btn_register.setOnClickListener {
            val nama = ed_nama.text.toString().trim()
            val email = ed_email.text.toString().trim()
            val pass = ed_pass.text.toString().trim()
            val no_telp = ed_no_telp.text.toString().trim()
            val alamat = ed_alamat.text.toString().trim()
            val desa = ed_desa.text.toString().trim()
            val kecamatan = ed_kecamatan.text.toString().trim()
            val kode_pos = ed_kode_pos.text.toString().trim()
            //validate here
            val user = User(nama = nama,password = pass ,email = email, no_telp = no_telp, alamat = alamat, desa = desa, kecamatan = kecamatan, kode_pos = kode_pos)
            registerViewModel.register(user)
        }
    }

}
