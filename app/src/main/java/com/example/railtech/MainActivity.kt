package com.example.railtech

import android.Manifest
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

class MainActivity : ComponentActivity() {

    private lateinit var wifiManager: WifiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize WifiManager
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        // Check and request location permission at runtime
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermissionsLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        setContent {
            RailtechTheme() {
//                    Column {
//                        RssiScreen(wifiManager)
                RssiScreen1(wifiManager)
                //}
            }


        }

    }

    // Permission request launcher
    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission granted, you can now access WiFi info
            } else {
                // Handle permission denial
            }
        }
    private fun startWifiScan() {
        wifiManager.startScan()
    }
}

@Composable
fun RssiScreen(wifiManager: WifiManager) {
    var rssi by remember { mutableStateOf(0) }
    var ssid by remember{ mutableStateOf("None")}
    // Launch a coroutine to update RSSI every second
    LaunchedEffect(Unit) {
        while (true) {
            ssid = getwifiSsid(wifiManager)
            rssi = getWifiRssi(wifiManager)
            delay(1000) // Update every second
        }
    }

    // Display the UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Connected Network RSSI $ssid", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "$rssi dBm", style = MaterialTheme.typography.headlineMedium)
    }
}
@Composable
fun RssiScreen1(wifiManager: WifiManager) {
    var scanResults by remember { mutableStateOf<List<ScanResult>>(emptyList()) }
    // Launch a coroutine to update scan results every few seconds
    LaunchedEffect(Unit) {
        while (true) {
            scanResults = getWifiScanResults(wifiManager)
            delay(10000) // Update every 10 seconds
        }
    }

    // Display the UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Available Networks", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        scanResults.forEach { result ->
            val ssid = if(result.SSID.isNullOrEmpty()) "Hidden Network" else result.SSID
            Text(text = "SSID: ${ssid}, RSSI: ${result.level} dBm", style = MaterialTheme.typography.bodyLarge)
        }
        Text("RSSI ranges from 0 to -90, the closer it is to 0 the stronger it is")
    }
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