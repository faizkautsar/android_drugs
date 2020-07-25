package com.example.drugs.webservices

import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.text.NumberFormat
import java.util.*

class Constants {
    companion object{

        val channelId = "drugs_channel"
        val channelName = "drug_notif_confirm"
        val channelDesc = "Push notification test"

        fun getToken(c : Context) : String {
            val s = c.getSharedPreferences("USER", MODE_PRIVATE)
            val token = s?.getString("TOKEN", "UNDEFINED")
            return token!!
        }

        fun setToken(context: Context, token : String){
            val pref = context.getSharedPreferences("USER", MODE_PRIVATE)
            pref.edit().apply {
                putString("TOKEN", token)
                apply()
            }
        }

        fun clearToken(context: Context){
            val pref = context.getSharedPreferences("USER", MODE_PRIVATE)
            pref.edit().clear().apply()
        }

        fun isValidEmail(email : String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        fun isValidPassword(pass : String) = pass.length >= 8

        fun setToIDR(num : Int) : String {
            val localeID = Locale("in", "ID")
            val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
            return formatRupiah.format(num)
        }
    }
}

