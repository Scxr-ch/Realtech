package com.example.railtech

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.railtech.ui.theme.RailtechTheme
import kotlinx.coroutines.delay
import android.os.Bundle
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Message
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.example.railtech.ui.theme.AppMain
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class RunningApp: Application(){
    override fun onCreate() {
        super.onCreate()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "running_channel",
                "running notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
class MainActivity : ComponentActivity() {
    private var isDarkMode by mutableStateOf(false) // State for dark mode
    private lateinit var wifiManager: WifiManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
        // Initialize WifiManager
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        // Check and request location permission at runtime
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermissionsLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        // Start the foreground service
        startService(Intent(applicationContext,WifiScanService::class.java).also{
            it.action = WifiScanService.Actions.START.toString()
            startService(it)
        })

        setContent {
            RailtechTheme(darkTheme = isDarkMode) {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.padding(it)) {
                        AppMain(this@MainActivity, this@MainActivity)
                        // Use your RssiScreen here
                        Box {
                            Button(onClick = {
                                stopService(
                                    Intent(
                                        applicationContext,
                                        WifiScanService::class.java
                                    ).also {
                                        it.action = WifiScanService.Actions.STOP.toString()
                                        stopService(it)
                                    })
                            }) { Text("stop service") }
                            Button(onClick = {
                                startService(
                                    Intent(
                                        applicationContext,
                                        WifiScanService::class.java
                                    ).also {
                                        it.action = WifiScanService.Actions.START.toString()
                                        startService(it)
                                    })
                            }) { Text("Start service again") }
                        }
//                            RssiScreen1(wifiManager)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

            }
        }

    }
//    fun showCamera() {
//        val options = ScanOptions()
//        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
//        options.setPrompt("Scan a QR code")
//        options.setCameraId(0)
//        options.setBeepEnabled(false)
//        options.setOrientationLocked(false)
//
//        qrCodeLauncher.launch(options)
//    }
//    private var textResult =mutableStateOf("")
//    private val qrCodeLauncher = registerForActivityResult(ScanContract()) { result ->
//        if (result.contents == null) {
//            Toast.makeText(this, "Scan canceled", Toast.LENGTH_SHORT).show()
//        } else {
//            textResult.value = result.contents
//        }
//    }
//
    // Permission request launcher
    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission granted, start the service
                startWifiScanService()
            } else {
                // Handle permission denial
            }
        }
//    private val requestPermissionLauncherCamera = registerForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted: Boolean ->
//        if (isGranted) {
//            showCamera()
//        } else {
//            Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show()
//        }
//    }
//    fun checkCamPermission(context: Context,activity: Activity) {
//        if (ContextCompat.checkSelfPermission(
//                context,
//                android.Manifest.permission.CAMERA,
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            showCamera()
//        } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.CAMERA)) {
//            Toast.makeText(context, "Camera permission is required to scan QR codes", Toast.LENGTH_SHORT).show()
//        } else {
//            requestPermissionLauncherCamera.launch(android.Manifest.permission.CAMERA)
//        }
//    }
    // Start the WifiScanService as a foreground service
    private fun startWifiScanService() {
        val intent = Intent(this, WifiScanService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }
}

//
//@Composable
//fun RssiScreen1(wifiManager: WifiManager) {
//
//    var scanResults by remember { mutableStateOf<List<ScanResult>>(emptyList()) }
//    // Launch a coroutine to update scan results every few seconds
//    LaunchedEffect(Unit) {
//        while (true) {
//            wifiManager.startScan()
//            scanResults = getWifiScanResults(wifiManager)
//            //scanResults = scanResults.filter { it.SSID == "NPWirelessx" }
//            println(scanResults)
//            delay(32000L) // Update every 10 seconds
//        }
//    }
//
//    // Display the UI
//    Text(text = "Available Networks", style = MaterialTheme.typography.headlineSmall)
//    LazyColumn(
//        modifier = Modifier
//            .padding(20.dp),
//        horizontalAlignment = Alignment.Start,
//        verticalArrangement = Arrangement.Center
//    ) {
//        items(scanResults){result ->
//            val ssid = if (result.SSID.isNullOrEmpty()) "Hidden Network" else result.SSID
//            Text(text = "SSID: ${ssid}, RSSI: ${result.level} dBm, MAC address: ${result.BSSID}", style = MaterialTheme.typography.bodyLarge)
//        }
//
//    }
//}

@Composable
fun Messagerow(result: ScanResult){
    val ssid = if (result.SSID.isNullOrEmpty()) "Hidden Network" else result.SSID
    Text(text = "SSID: ${ssid}, RSSI: ${result.level} dBm, MAC address: ${result.BSSID}", style = MaterialTheme.typography.bodyLarge)
}
fun getWifiScanResults(wifiManager: WifiManager): List<ScanResult> {
    return wifiManager.scanResults // List of ScanResults with RSSI and other details
}

@Suppress("DEPRECATION")
fun getWifiRssi(wifiManager: WifiManager): Int {
    return wifiManager.connectionInfo.rssi
}
@Suppress("DEPRECATION")
fun getwifiSsid(wifiManager: WifiManager): String {
    val ssid = wifiManager.connectionInfo.ssid
    return if(ssid.isNullOrEmpty()||ssid == "") "Hidden" else ssid
}

//@Preview(showBackground = true)
//@Composable
//fun login_screenPreview() {
//    RailtechTheme {
//        AppMain()
//    }
//}





