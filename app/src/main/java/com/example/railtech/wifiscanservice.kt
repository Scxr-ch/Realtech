package com.example.railtech

import android.app.ForegroundServiceStartNotAllowedException
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.core.content.PermissionChecker
import kotlinx.coroutines.*
class WifiScanService : Service() {
    private lateinit var wifiManager: WifiManager

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification =
            NotificationCompat.Builder(this, "running_channel").setSmallIcon(R.drawable.ic_wifi)
                .setContentText("WifiScan is running as a Foreground Service").build()
        startForeground(1, notification)
    }

    enum class Actions {
        START, STOP
    }

//    override fun onCreate() {
//        super.onCreate()
//        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//        CoroutineScope(Dispatchers.IO).launch {
//            while (true) {
//                wifiManager.startScan()
//                val scanResults = wifiManager.scanResults
//                // Process scan results
//                println(scanResults)
//                delay(32000) // Scan every 30 seconds
//            }
//        }
//    }
}