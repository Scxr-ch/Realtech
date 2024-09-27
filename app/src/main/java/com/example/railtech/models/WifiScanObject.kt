package com.example.railtech.models;

import android.net.wifi.WifiSsid

data class WifiScanObject(
    val user: String,
    val accessPoints: List<AccessPoint>
)

data class AccessPoint (
    val ssid: String,
    val bssid: String,
    val signalStrength: Int,
    val frequency: Int
)