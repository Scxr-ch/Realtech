package com.example.railtech

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.railtech.models.CheckInObject
import com.example.railtech.network.sendCheckInData
import com.example.railtech.ui.theme.ApplicationIDField
import com.example.railtech.ui.theme.Orbar
import com.example.railtech.ui.theme.SessionState
import com.example.railtech.ui.theme.SubmitButton
import com.example.railtech.ui.theme.WorkIDField
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object User {
    var name by mutableStateOf("")
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActualLoginScreen(onNavigateToConfirmation: () -> Unit) {

    // State to store the scan result
    val textResult = rememberSaveable { mutableStateOf("") }
//
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        var IDisError by rememberSaveable { mutableStateOf(false) }
        var QRisError by rememberSaveable { mutableStateOf(false) }
//        var username by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }

        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = {
//                        Text(
//                            text = "Login",
//                            fontSize = 40.sp,
////                            style = MaterialTheme.typography.titleLarge,
//                            modifier = Modifier.padding(16.dp)
//                        )
//                    },
//                    actions = {
//                        Image(
//                            painter = painterResource(id = R.drawable.map),
//                            contentDescription = null,
//                            modifier = Modifier.size(50.dp)
//                        )
//                    },
//                    colors = TopAppBarDefaults.topAppBarColors(
//                        containerColor = MaterialTheme.colorScheme.primaryContainer,
//                        titleContentColor = MaterialTheme.colorScheme.primary,
//                    ),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .statusBarsPadding()
//                )
//            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
//                    .padding(top = 70.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Login",
                    fontSize = 40.sp,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(16.dp)
                )
//                Spacer(modifier = Modifier.height(10.dp))

//                // QR Code Button
//                Button(
//                    onClick = {
//                        // QR code scanning logic
////                        val scannedQRCode = "userScannedQRCode" // Replace with actual logic
////                        if (scannedQRCode == "expectedQRCodeValue") {
////                            QRisError = false
////                        } else {
////                            QRisError = true
////                        }
////                        checkCamPermission(context = context, activity = activity)
//
//
//                    },
//                    modifier = Modifier
//                        .width(300.dp)
//                        .height(80.dp)
//                ) {
//                    Icon(
//                        imageVector = ImageVector.vectorResource(id = R.drawable.scanner),
//                        contentDescription = null,
//                        modifier = Modifier.size(50.dp)
//                    )
//                    Text(
//                        text = "Scan QR Code",
//                        fontSize = 25.sp,
//                        modifier = Modifier.padding(start = 8.dp)
//                    )
//                }
//
//                if (QRisError) {
//                    Text(
//                        text = "Invalid QR Code. Please try again.",
//                        color = MaterialTheme.colorScheme.error,
//                        style = MaterialTheme.typography.bodyLarge,
//                        fontSize = 20.sp,
//                        modifier = Modifier.padding(start = 16.dp)
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(20.dp))

//                Orbar()

//                Spacer(modifier = Modifier.height(20.dp))

                // Application ID and Work ID Fields in a Box**
                Box(
                    modifier = Modifier
//                        .padding(16.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Column {
                        // Application ID Field
//                        ApplicationIDField(applicationID) { newValue ->
//                            applicationID = newValue
//                        }

                        TextField(
                            value = User.name,
                            onValueChange = { newValue ->
                                User.name = newValue
                            },
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.shapes.large
                                ),
                            leadingIcon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_person_24),
                                    contentDescription = null
                                )
                            },
                            placeholder = {
                                Text(
                                    text = "Username",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        )

                        // Work ID Field
//                        WorkIDField(workID) { newValue ->
//                            workID = newValue
//                        }
                        TextField(
                            value = password,
                            onValueChange = { newValue ->
                                password = newValue
                            },
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.shapes.large
                                ),
                            leadingIcon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_person_24),
                                    contentDescription = null
                                )
                            },
                            placeholder = {
                                Text(
                                    text = "Password",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        )
                    }
                }

                // Conditional error message for both fields**
                if (IDisError) {
                    Text(
                        text = "Invalid Application ID or Work ID. Please try again.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 20.sp,
//                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(35.dp))

                // Submit Button
                SubmitButton {
                    if (password == "a") { // it doesnt matter what value username is
                        onNavigateToConfirmation() // Navigate to confirmation screen
                    } else {
                        IDisError = true
                    }
                }

//                Spacer(modifier = Modifier.height(20.dp))

//                Navbar()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    ActualLoginScreen(
        onNavigateToConfirmation = {})
}