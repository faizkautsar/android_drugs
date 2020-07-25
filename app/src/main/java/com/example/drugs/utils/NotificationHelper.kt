package com.example.drugs.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.drugs.R
import com.example.drugs.ui.main.MainActivity
import com.example.drugs.webservices.Constants

object NotificationHelper {

    fun displayNotification(context: Context, title: String, body: String, i : Map<String, String>) {
        if(Constants.getToken(context) != "UNDEFINED"){
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT)

            val mBuilder = NotificationCompat.Builder(context, Constants.channelId)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(title)
//            .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            val mNotificationMgr = NotificationManagerCompat.from(context)
            mNotificationMgr.notify(1, mBuilder.build())
        }
    }
}