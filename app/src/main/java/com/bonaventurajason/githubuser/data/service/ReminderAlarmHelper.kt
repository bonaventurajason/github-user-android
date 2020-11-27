package com.bonaventurajason.githubuser.data.service

import android.app.*
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.bonaventurajason.githubuser.R
import com.bonaventurajason.githubuser.helper.Constant.REMINDER_CHANNEL_ID
import com.bonaventurajason.githubuser.helper.Constant.REMINDER_CHANNEL_NAME
import com.bonaventurajason.githubuser.helper.Constant.REMINDER_MESSAGE
import com.bonaventurajason.githubuser.helper.Constant.REMINDER_TITLE
import java.util.*

object ReminderAlarmHelper {

    fun showReminderNotification(
        context: Context,
        title: String,
        message: String,
        notificationID: Int,
        pendingIntent: PendingIntent
    ) {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val builder = NotificationCompat.Builder(
            context, REMINDER_CHANNEL_ID
        ).apply {
            setContentTitle(title)
            setContentText(message)
            setSound(uri)
            setAutoCancel(true)
            setSmallIcon(R.mipmap.ic_launcher_round)
            setDefaults(Notification.DEFAULT_SOUND)
        }
        builder.setContentIntent(pendingIntent)
        val notification = builder.build()
        notificationManager.notify(notificationID, notification)

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val notificationChannel = NotificationChannel(
            REMINDER_CHANNEL_ID,
            REMINDER_CHANNEL_NAME,
            IMPORTANCE_HIGH
        ).apply {
            enableLights(true)
            vibrationPattern = longArrayOf(0, 1000, 1000, 500, 1000)
            enableVibration(true)
        }

        notificationManager.createNotificationChannel(notificationChannel)
    }

    fun setAlarm(
        context: Context,
        title: String,
        message: String,
        requestCode: Int,
        time: Calendar
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderAlarmReceiver::class.java).apply {
            putExtra(REMINDER_TITLE, title)
            putExtra(REMINDER_MESSAGE, message)
        }
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            time.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun stopAlarm(
        context: Context,
        requestCode: Int
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0).also {
            it.cancel()
        }

        alarmManager.cancel(pendingIntent)
    }

}