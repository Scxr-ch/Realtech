package com.example.railtech

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import com.example.railtech.ui.theme.NavbarComposable

@Composable
fun GPSScreen(onClickMap: () -> Unit, onClickGPS: () -> Unit) {
    val context = LocalContext.current
//    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
//    val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    Scaffold(
        bottomBar = {
            NavbarComposable(
                onClickMap = onClickMap,
                onClickGPS = onClickGPS,
                selectMap = false,
                selectGPS = true
            )
        }
    ) { paddingValues ->
        var showDialog by remember { mutableStateOf(false) }
        val context = LocalContext.current

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Button(onClick = {
                    showDialog = true
                    Log.d("NotificationTest", "Button clicked, attempting to show notification")
                    val builder = NotificationCompat.Builder(context, "workzones_channel")
                        .setSmallIcon(R.drawable.workzone_notice_icon)
                        .setContentTitle("Workzones notice")
                        .setContentText("Please return back to your dedicated workzone!")
                    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.notify(2, builder.build())
                }) {
                    Text(text = "Testing")
                }

                // AlertDialog is now within the Box
                if (showDialog) {
                    Alert_screen(onDismiss = { showDialog = false })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGPSScreen() {
    GPSScreen(onClickMap = {}, onClickGPS = {})
}
