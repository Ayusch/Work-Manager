package com.example.workmanagersample

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.R
import android.support.v4.app.NotificationCompat
import android.app.NotificationChannel
import android.app.NotificationManager
import android.util.EventLog
import android.util.Log
import androidx.work.Data
import org.greenrobot.eventbus.EventBus

/**
 *
 *Created by ayuschj on 3/4/19
 *
 */

class ImageDownloadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    val notificationManager: NotificationManager =
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    @SuppressLint("RestrictedApi")
    override fun doWork(): Result {
        var i = 1

        while (i <= 100) {
            Thread.sleep(100)
            displayNotification(i)
                EventBus.getDefault().post(ProgressEvent(i))
            i += 1
        }
        notificationManager.cancel(1)

        val data = Data.Builder().putInt("prog", 100).build()
        return Result.Success(data)
    }

    private fun displayNotification(prog: Int) {
        Log.d("image", "Porg Log: $prog")


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel("sc", "sc", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, "sc")
        notification.setProgress(100, prog, true)
        notification.setContentTitle("This is a title")
            .setContentText("$prog/100")
            .setSmallIcon(R.drawable.ic_delete)


        notificationManager.notify(1, notification.build())
    }

//
//    override fun doWork(): ListenableWorker.Result {
//        displayNotification()
//        val manager = ProgressManager(applicationContext)
//        var i = 0
//        while (i < 10) {
//            i++
//            try {
//                Thread.sleep(1000)
//                manager.updateProgress(i)
//            } catch (e: InterruptedException) {
//                e.printStackTrace()
//            }
//
//        }
//        Log.i("Ansh", "worker's job is finished")
//        // Indicate success or failure with your return value:
//        return ListenableWorker.Result.success()
//
//        // (Returning Result.retry() tells WorkManager to try this task again
//        // later; Result.failure() says not to try again.)
//    }
//
//    fun displayNotification() {
//        val notificationManager: NotificationManager =
//            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            val channel =
//                NotificationChannel("simplifiedcoding", "simplifiedcoding", NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        val notification = NotificationCompat.Builder(applicationContext, "simplifiedcoding")
//        notification.setProgress(100, 5, true)
//        notification.setContentTitle("This is a title")
//            .setContentText("This is a text")
//            .setSmallIcon(R.drawable.ic_delete)
//
//
//        notificationManager.notify(1, notification.build())
//    }

}
