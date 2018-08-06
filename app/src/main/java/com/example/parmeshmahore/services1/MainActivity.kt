package com.example.parmeshmahore.services1

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var serviceIntent : Intent? = null
    var myService : MyService? = null
    var isServiceBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        serviceIntent = Intent(applicationContext, MyService::class.java)

        startService.setOnClickListener {
            Toast.makeText(applicationContext, "Hello World", Toast.LENGTH_LONG).show()
            startService(serviceIntent)
        }

        stopService.setOnClickListener {
            Toast.makeText(applicationContext, "Stop Service", Toast.LENGTH_LONG).show()
            if (serviceIntent != null) {
                stopService(serviceIntent)
            } else {
                Log.d("My-Service", "Service is not started");
            }
        }

        bindService.setOnClickListener {
            if (!isServiceBound) {
                isServiceBound = true
                bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
            } else {
                random_tv.text = "Service is already bound"
            }
        }

        unbindService.setOnClickListener {
            if (isServiceBound) {
                unbindService(connection)
                isServiceBound = false
            } else {
                random_tv.text = "Service is not bound"
            }
        }

        btn_random_number.setOnClickListener {
            if (isServiceBound) {
                random_tv.text = "Random Number is ${myService?.getNumber()}"
            } else {
                random_tv.text = "Service not bound"
            }
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d("My-Service", "onServiceDisconnected")
            isServiceBound = false
        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder  = p1 as MyService.ServiceBinder
            myService = binder.getService()
            Log.d("My-Service", "onServiceConnected")
            isServiceBound = true
        }
    }
}