package com.example.workmanagersample

import android.content.Context
import android.os.Handler
import android.support.v4.app.NotificationCompat
import android.os.Looper
import android.os.Message
import android.support.v4.app.NotificationManagerCompat
import android.app.NotificationManager
import android.app.NotificationChannel
import android.support.v4.content.ContextCompat.getSystemService
import android.util.Log


/**
 *
 *Created by ayuschj on 3/4/19
 *
 */

class ProgressManager(context: Context) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val notificationBuilder = NotificationCompat.Builder(context, "sc")

    private var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val channel =
                    NotificationChannel("sc", "sc", NotificationManager.IMPORTANCE_DEFAULT)
                notificationManager.createNotificationChannel(channel)
            }

            notificationBuilder.setProgress(100, msg.arg1, true)
            val notification = notificationBuilder
                .setContentTitle("Title")
                .setContentText("Message")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .build()

            notificationManager.notify(1000, notification)
        }
    }


    fun updateProgress(prog: Int) {
        val msg = Message()
        msg.arg1 = prog
        Log.d("image", "logging progress = " + prog)
        handler.sendMessageDelayed(msg, 1000)
    }
}