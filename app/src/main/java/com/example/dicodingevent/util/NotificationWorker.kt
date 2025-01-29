package com.example.dicodingevent.util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.dicodingevent.R
import com.example.dicodingevent.data.network.response.Events
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotificationWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val eventRepository = Injection.provideRepository(context)

    override suspend fun doWork(): Result {
        return try {
            val upcomingEvent = fetchUpcomingEvent()
            if (upcomingEvent != null) {
                showNotification(upcomingEvent)
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private suspend fun fetchUpcomingEvent(): Events? {
        return withContext(Dispatchers.IO) {
            try {
                val events = eventRepository.getEvent(1).listEvents
                events.firstOrNull()
            } catch (e: Exception) {
                null
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun showNotification(event: Events) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "event_channel",
                "Event Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, "event_channel")
            .setSmallIcon(R.drawable.ic_date)
            .setContentTitle("Upcoming Event: ${event.name}")
            .setContentText(event.beginTime)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}