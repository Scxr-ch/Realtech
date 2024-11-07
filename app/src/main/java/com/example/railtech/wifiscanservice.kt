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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.core.content.PermissionChecker
import com.example.railtech.models.AccessPoint
import com.example.railtech.models.WifiScanObject
import com.example.railtech.network.sendWifiScanData
import com.google.gson.Gson
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

    val scope = CoroutineScope(Dispatchers.IO)

    private fun processWifiScanResults(scanResults: List<ScanResult>) {
        val thingToSend = WifiScanObject(
            user = User.name,
            accessPoints = List(scanResults.size) { index ->
                AccessPoint(
                    bssid = scanResults[index].BSSID,
                    signalStrength = scanResults[index].level,
                    frequency = scanResults[index].frequency,
                    ssid = scanResults[index].SSID,
                )
            }
        )
        scope.launch {
//            Log.d("WifiScanService", "thingToSend: $thingToSend")

            // Convert to JSON
            val gson = Gson()
            val jsonOutput = gson.toJson(thingToSend)
            Log.d("WifiScanService", "Thing to send: $jsonOutput")

            val response = sendWifiScanData(thingToSend)
            Log.d("WifiScanService", "Response: $response")

            if (response != null) {
                // Create and send a broadcast with the response
                val dataIntent = Intent("com.example.UPDATE_UI")
                dataIntent.putExtra("response_key", response) // Adjust based on your response
                sendBroadcast(dataIntent)
                // ... write other elements of the list ...
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                wifiManager.startScan()
                val scanResults = wifiManager.scanResults
                // Process scan results
                println(scanResults)

                processWifiScanResults(scanResults)
                delay(32000) // Scan every 30 seconds
            }
        }
    }
}