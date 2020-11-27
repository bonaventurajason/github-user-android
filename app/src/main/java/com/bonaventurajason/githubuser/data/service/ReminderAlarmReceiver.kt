package com.bonaventurajason.githubuser.data.service

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bonaventurajason.githubuser.helper.Constant.REMINDER_ID_REPEATING
import com.bonaventurajason.githubuser.helper.Constant.REMINDER_MESSAGE
import com.bonaventurajason.githubuser.helper.Constant.REMINDER_TITLE
import com.bonaventurajason.githubuser.ui.MainActivity

class ReminderAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra(REMINDER_TITLE)
        val message = intent.getStringExtra(REMINDER_MESSAGE)

        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)

        ReminderAlarmHelper.showReminderNotification(
            context,
            title ?: "Title",
            message ?: "Message",
            REMINDER_ID_REPEATING,
            pendingIntent
        )
    }
}