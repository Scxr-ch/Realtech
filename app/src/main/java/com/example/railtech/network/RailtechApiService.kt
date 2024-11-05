package com.example.railtech.network

import com.example.railtech.models.CheckInObject
import com.example.railtech.models.StepsInfoObject
import com.example.railtech.models.WifiScanObject
import com.example.railtech.models.WifiScanResponse
import okhttp3.OkHttpClient

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

private const val BASE_URL =
    "https://railtech-server.onrender.com"

// Creates a new retrofit project
//private val retrofit = Retrofit.Builder()
//    // tells Retrofit to fetch a JSON response from the web service and return it
//    // as a String. (ScalarsConverter supports strings and other primitive types)
//    .addConverterFactory(ScalarsConverterFactory.create())
//    .baseUrl(BASE_URL)
//    .build()

// Set up OkHttpClient with custom timeouts
val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(90, TimeUnit.SECONDS)   // Connection timeout
    .readTimeout(90, TimeUnit.SECONDS)      // Read timeout
    .writeTimeout(90, TimeUnit.SECONDS)     // Write timeout
    .build()

private val retrofit = Retrofit.Builder()
    // Now uses kotlinx.serialization instead of the ScalarConverterFactory above
//    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()


interface RailtechApiService {
    // When the getPhotos() method is invoked, Retrofit appends the endpoint photos to the base URL
    // which you defined in the Retrofit builder—used to start the request.
    @POST("update-coordinate")
    suspend fun sendWifiScanData(@Body wifiScanObject: WifiScanObject) : Response<WifiScanResponse>

    @POST("update_steps")
    suspend fun sendStepsData(@Body stepsInfoObject: StepsInfoObject) : Response<Void>

    @POST("check-in")
    suspend fun sendCheckInData(@Body checkInObject: CheckInObject) : Response<Void>

}

// Warning: Singleton pattern is not a recommended practice
// the public singleton method that there only be must be one of
object RailtechApi {
    // "lazy initialization" is when object creation is delayed until you need that object
    val retrofitService : RailtechApiService by lazy {
        // retrofitService variable using the retrofit.create()
        retrofit.create(RailtechApiService::class.java)
    }
}