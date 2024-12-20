package com.example.railtech.network

import android.util.Log
import com.example.railtech.models.CheckInObject
import com.example.railtech.models.StepsInfoObject
import com.example.railtech.models.WifiScanObject
import com.example.railtech.models.WifiScanResponse

suspend fun sendWifiScanData(wifiScanObject: WifiScanObject) : WifiScanResponse? {

    // Convert the WifiScanObject to a JSON string
//    val requestBody = Gson().toJson(wifiScanObject)
    try {
        val response = RailtechApi.retrofitService.sendWifiScanData(wifiScanObject)

        if (response.isSuccessful) {
            println("POST request successful: ${response.code()}")
            val responseBody = response.body()
            println("Response Body: $responseBody")
            return responseBody
        } else {
            println("Error: ${response.code()}")
            Log.d("Response", "Error: ${response.errorBody()?.string()}")
        }
    } catch (e: Exception) {
        println("POST request failed: ${e.message}")
    }
    return null
}

suspend fun sendCheckInData(checkInObject: CheckInObject) {

    // Convert the WifiScanObject to a JSON string
//    val requestBody = Gson().toJson(wifiScanObject)
    try {
        val response = RailtechApi.retrofitService.sendCheckInData(checkInObject)

        if (response.isSuccessful) {
            println("POST request successful: ${response.code()}")
        } else {
            println("Error: ${response.code()}")
        }
    } catch (e: Exception) {
        println("POST request failed: ${e.message}")
    }

}

suspend fun sendStepsData(stepsInfoObject: StepsInfoObject) {

    // Convert the WifiScanObject to a JSON string
//    val requestBody = Gson().toJson(wifiScanObject)
    try {
        val response = RailtechApi.retrofitService.sendStepsData(stepsInfoObject)

        if (response.isSuccessful) {
            println("POST request successful: ${response.code()}")
        } else {
            println("Error: ${response.code()}")
        }
    } catch (e: Exception) {
        println("POST request failed: ${e.message}")
    }

}