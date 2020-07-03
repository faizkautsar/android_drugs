package com.example.drugs.ui.login

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
import com.example.drugs.ui.main.MainActivity
import com.example.drugs.viewmodels.UserState
import com.example.drugs.viewmodels.UserViewModel
import com.example.drugs.webservices.Constants
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.listenToState().observer(this, Observer { handleUI(it) })

        login()

        btn_register.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun handleUI(it : UserState){
        when(it){
            is UserState.ShowToast -> toast(it.message)
            is UserState.IsLoading -> {
                if (it.state){
                    btn_login.disabled()
                }else{
                    btn_login.enabled()
                }
            }
            is UserState.SuccessLogin -> {
                Constants.setToken(this@LoginActivity, "Bearer ${it.token}")
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }
        }
    }

    private fun login(){
        btn_login.setOnClickListener {
            val email = ed_email.text.toString().trim()
            val pass = ed_pass.text.toString().trim()
            userViewModel.login(email, pass)
        }
    }

    private fun toast(m : String) = Toast.makeText(this, m, Toast.LENGTH_LONG).show()
}
