package com.example.drugs.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import coil.api.load
import com.example.drugs.R
import com.example.drugs.extensions.gone
import com.example.drugs.extensions.toast
import com.example.drugs.extensions.visible
import com.example.drugs.ui.login.LoginActivity
import com.example.drugs.ui.main.home.HomeFragment
import com.example.drugs.ui.main.law.PeraturanFragment
import com.example.drugs.ui.main.prevention.UpayaFragment
import com.example.drugs.ui.profile.ProfileActivity
import com.example.drugs.ui.report.ReportActivity
import com.example.drugs.webservices.Constants
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(),  NavigationView.OnNavigationItemSelectedListener {
    companion object{
        var openFirst = false
        var navStatus = -1
    }
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initComp()
        observeTitle()
        if(savedInstanceState == null){
            openFirst = true
            val item = nav_view.getMenu().getItem(0).setChecked(true)
            onNavigationItemSelected(item)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment : Fragment? = null
        when (item.itemId) {
            R.id.nav_home -> {
                if(navStatus == 0 && !openFirst){
                    drawer_layout.closeDrawer(GravityCompat.START)
                }else{
                    setTitle("Home")
                    navStatus = 0
                    openFirst = false
                    fragment = HomeFragment()
                }
            }
            R.id.nav_upaya -> {
                if(navStatus == 1 && !openFirst){
                    drawer_layout.closeDrawer(GravityCompat.START)
                }else{
                    setTitle("Upaya")
                    openFirst = false
                    navStatus = 1
                    fragment = UpayaFragment()
                }
            }
            R.id.nav_hukum -> {
                if(navStatus == 2 && !openFirst){
                    drawer_layout.closeDrawer(GravityCompat.START)
                }else{
                    setTitle("Hukum")
                    openFirst = false
                    navStatus = 2
                    fragment = PeraturanFragment()
                }
            }

            else -> {
                setTitle("Home")
                openFirst = false
                navStatus = 0
                fragment = HomeFragment()
            }
        }

        if(fragment != null){
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            ft.replace(R.id.screen_container, fragment)
            ft.commit()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initComp(){
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
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


    private fun popup(){
        AlertDialog.Builder(this@MainActivity).apply {
            setNegativeButton("profil"){dialog, _ ->
                dialog.dismiss()
                startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
            }
            setPositiveButton("logout"){dialog, _ ->
                dialog.dismiss()
                logout()
            }
        }.show()
    }

    private fun logout() {
        Constants.clearToken(this@MainActivity)
        userPhotoVisibility()
    }

    private fun userPhotoVisibility(){
        if(Constants.getToken(this) != "UNDEFINED"){
            user_photo.visible()
            user_photo.setOnClickListener {
                popup()
            }
            setUserPhoto()
        }else{
            user_photo.gone()
        }
    }

    override fun onResume() {
        userPhotoVisibility()
        super.onResume()
    }

    private fun setTitle(title: String){
        mainViewModel.setTitle(title)
    }

    private fun observeTitle(){
        mainViewModel.getTitle().observe(this, Observer { handleTitleChange(it) })
    }

    private fun handleTitleChange(title: String){
        tv_title.text = title
    }

}
