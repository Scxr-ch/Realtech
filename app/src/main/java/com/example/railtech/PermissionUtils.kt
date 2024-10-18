package com.example.railtech

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.example.railtech.MainActivity
// Isaacs functions
//private var textResult = mutableStateOf("")
//
//private val qrCodeLauncher = registerForActivityResult(ScanContract()) { result ->
//    if (result.contents == null) {
//        Toast.makeText(this, "Scan canceled", Toast.LENGTH_SHORT).show()
//    } else {
//        textResult.value = result.contents
//    }
//}

//fun showCamera() {
//    val options = ScanOptions()
//    options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
//    options.setPrompt("Scan a QR code")
//    options.setCameraId(0)
//    options.setBeepEnabled(false)
//    options.setOrientationLocked(false)
//
//    qrCodeLauncher.launch(options)
//}

//private val requestPermissionLauncherCamera = registerForActivityResult(
//    ActivityResultContracts.RequestPermission()
//) { isGranted: Boolean ->
//    if (isGranted) {
//        showCamera()
//    } else {
//        Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show()
//    }
//}

fun checkCamPermission(context: Context,activity: Activity) {
    if (ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.CAMERA,
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        showCamera()
    } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.CAMERA)) {
        Toast.makeText(context, "Camera permission is required to scan QR codes", Toast.LENGTH_SHORT).show()
    } else {
        ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.CAMERA),101)
    }
}