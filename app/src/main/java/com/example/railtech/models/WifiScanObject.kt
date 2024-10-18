package com.example.railtech.models


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

data class StepsInfoObject(
    val user: String,
    val directions: List<String>,
    val steps: Int
)

