package com.example.parmeshmahore.services1

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlin.concurrent.thread

class MyService : Service() {

    var randomNumber : Int = 0

    inner class ServiceBinder : Binder() {
        fun getService() : MyService {
            return this@MyService
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.d("My-Service", "onBind called")
        return ServiceBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("My-Service", "onStartCommand")
        thread {

            while (randomNumber < 20) {
                Log.d("My-Service", "Count: $randomNumber")
                randomNumber++
                Thread.sleep(1000)
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("My-Service", "on Destroy")
    }


    fun getNumber() : Int {
        return randomNumber
    }
}