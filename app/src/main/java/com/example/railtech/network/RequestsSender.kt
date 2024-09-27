package com.example.railtech.network

import com.example.railtech.models.WifiScanObject

suspend fun sendWifiScanData(wifiScanObject: WifiScanObject) {

    // Convert the WifiScanObject to a JSON string
//    val requestBody = Gson().toJson(wifiScanObject)
    try {
        val response = RailtechApi.retrofitService.sendWifiScanData(wifiScanObject)

        if (response.isSuccessful) {
            println("POST request successful: ${response.code()}")
        } else {
            println("Error: ${response.code()}")
        }
    } catch (e: Exception) {
        println("POST request failed: ${e.message}")
    }

}

