package com.example.drugs.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import coil.api.load
import com.example.drugs.R
import com.example.drugs.extensions.gone
import com.example.drugs.extensions.toast
import com.example.drugs.extensions.visible
import com.example.drugs.ui.login.LoginActivity
import com.example.drugs.ui.report.ReportActivity
import com.example.drugs.webservices.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home,
            R.id.nav_side_effect,
            R.id.nav_hukum,
            R.id.nav_rehabilitation,
            R.id.nav_upaya
        ), drawer_layout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)
    }

    private fun setUserPhoto(){
        user_photo.load("https://www.bluemaumau.org/sites/default/files/default_images/default.png")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_report -> {
                if(Constants.getToken(this).equals("UNDEFINED")){
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                }else{
                    startActivity(Intent(this@MainActivity,ReportActivity::class.java))
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        if(Constants.getToken(this) != "UNDEFINED"){
            user_photo.visible()
            setUserPhoto()
        }else{
            user_photo.gone()
        }
    }

}
