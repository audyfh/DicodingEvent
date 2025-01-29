package com.example.dicodingevent.util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.dicodingevent.R

class NotificationReceiver : BroadcastReceiver() {

    @SuppressLint("ObsoleteSdkInt")
    override fun onReceive(context: Context, intent: Intent) {
        val eventName = intent.getStringExtra("event_name") ?: "Event"
        val eventTime = intent.getStringExtra("event_time") ?: "Unknown time"

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "upcoming_event_channel"


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Upcoming Events",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }


        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Upcoming Event: $eventName")
            .setContentText("Starts at: $eventTime")
            .setSmallIcon(R.drawable.ic_date)
            .build()

        notificationManager.notify(eventName.hashCode(), notification)
    }
}