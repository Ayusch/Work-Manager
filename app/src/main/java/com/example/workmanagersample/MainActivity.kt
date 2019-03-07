package com.example.workmanagersample

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import java.util.*
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
//
//    private var handler: Handler = object : Handler(Looper.getMainLooper()) {
//        override fun handleMessage(msg: Message) {
//            super.handleMessage(msg)
//            runOnUiThread {
//                progressBar.progress = msg.arg1
//            }
//        }
//    }


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = Data.Builder()
        data.putString("string", "Hello Message")
        val request = OneTimeWorkRequest.Builder(ImageDownloadWorker::class.java).addTag("sc")
            .setInputData(data.build())
            .build()
        val workManager = WorkManager.getInstance()
        btn_start.setOnClickListener {
            workManager.beginWith(
                request

            ).enqueue()
        }
        workManager.getWorkInfoByIdLiveData(request.id).observe(this, Observer { info ->
            if (info?.state?.isFinished!!) {
                //do something when finished.
            }
        })

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ProgressEvent) {/* Do something */
        progressBar.progress = event.progress
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("destroyed", "Activity destroyed.")
    }

}

