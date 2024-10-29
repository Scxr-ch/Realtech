package com.example.railtech

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
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
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ){
            Text(text = "GPS Screen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGPSScreen() {
    GPSScreen(onClickMap = {}, onClickGPS = {})
}
