package com.example.drugs.ui.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.drugs.R
import com.example.drugs.ui.register.RegisterActivity
import com.example.drugs.extensions.disabled
import com.example.drugs.extensions.enabled
import com.example.drugs.extensions.toast
import com.example.drugs.ui.main.MainActivity
import com.example.drugs.viewmodels.UserState
import com.example.drugs.viewmodels.UserViewModel
import com.example.drugs.webservices.Constants
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        observe()
        login()
        goToRegisterActivity()
    }

    private fun observe(){
        observeState()
    }

    private fun observeState() = loginViewModel.getState().observer(this, Observer { handleUI(it) })

    private fun handleUI(it : LoginState){
        when(it){
            is LoginState.ShowToast -> toast(it.message)
            is LoginState.Loading -> isLoading(it.state)
            is LoginState.Success -> {
                Constants.setToken(this@LoginActivity, "Bearer ${it.token}")
                finish()
//                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }
        }
    }

    private fun isLoading(b: Boolean){
        if(b){
            btn_login.disabled()
            btn_register.disabled()
        }else{
            btn_login.enabled()
            btn_register.enabled()
        }
    }

    private fun login(){
        btn_login.setOnClickListener {
            val email = ed_email.text.toString().trim()
            val pass = ed_pass.text.toString().trim()
            loginViewModel.login(email, pass)
        }
    }

    private fun goToRegisterActivity(){
        btn_register.setOnClickListener {
            startActivityForResult(Intent(this@LoginActivity, RegisterActivity::class.java), 7)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 7){
            finish()
        }
    }

}
