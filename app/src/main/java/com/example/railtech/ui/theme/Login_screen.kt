package com.example.railtech.ui.theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.example.railtech.MainActivity.checkCamPermission

import com.example.railtech.R

@Composable
fun Navbar() {
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()  // Ensures padding for system navigation bar
                    .background(MaterialTheme.colorScheme.surfaceVariant)  // Extend background to the bottom
            ) {
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.map),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "Map")
                    },
                    selected = true,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_person_24),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "Profile")
                    },
                    selected = false,
                    onClick = {}
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Apply padding from the Scaffold
        ) {
            // Your screen content here
        }
    }
}

@Composable
fun SubmitButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(300.dp)
            .height(50.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = "Submit",
            fontSize = 30.sp,
        )
    }
}

@Composable
fun ApplicationIDField(applicationID: String, onValueChange: (String) -> Unit) {
    TextField(
        value = applicationID,
        onValueChange = onValueChange,
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
                text = "Application ID",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    )
}

@Composable
fun WorkIDField(workID: String, onValueChange: (String) -> Unit) {
    TextField(
        value = workID,
        onValueChange = onValueChange,
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
                text = "Work ID",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    )
}
@Composable
fun Orbar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Divider(
            modifier = Modifier
                .weight(1f)
                .padding(top = 50.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            thickness = 2.dp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "or",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 50.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Divider(
            modifier = Modifier
                .weight(1f)
                .padding(top = 50.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            thickness = 2.dp
        )
    }
}
@Composable
fun AppMain() {
    var currentScreen by rememberSaveable { mutableStateOf("login_screen") }

    // Conditionally render screens based on the currentScreen state
    when (currentScreen) {
        "login_screen" -> login_screen(onNavigateToConfirmation = {
            currentScreen = "confirmation_screen"
        })
        "confirmation_screen" -> ConfirmationPage(onNavigateToLogin = {
            currentScreen = "login_screen"
        })
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun login_screen(onNavigateToConfirmation: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        var IDisError by rememberSaveable { mutableStateOf(false) }
        var QRisError by rememberSaveable { mutableStateOf(false) }
        var applicationID by rememberSaveable { mutableStateOf("") }
        var workID by rememberSaveable { mutableStateOf("") }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "LTA PAL",
                            fontSize = 40.sp,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    },
                    actions = {
                        Image(
                            painter = painterResource(id = R.drawable.map),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(top = 70.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(25.dp))

                // QR Code Button
                Button(
                    onClick = {
                        // QR code scanning logic
//                        val scannedQRCode = "userScannedQRCode" // Replace with actual logic
//                        if (scannedQRCode == "expectedQRCodeValue") {
//                            QRisError = false
//                        } else {
//                            QRisError = true
//                        }
                        checkCamPermission(this@MainActivity)
                    },
                    modifier = Modifier
                        .width(300.dp)
                        .height(80.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.scanner),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = "Scan QR Code",
                        fontSize = 25.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                if (QRisError) {
                    Text(
                        text = "Invalid QR Code. Please try again.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Orbar()

                Spacer(modifier = Modifier.height(20.dp))

                // Application ID and Work ID Fields in a Box**
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        // Application ID Field
                        ApplicationIDField(applicationID) { newValue ->
                            applicationID = newValue
                        }

                        // Work ID Field
                        WorkIDField(workID) { newValue ->
                            workID = newValue
                        }
                    }
                }

                // Conditional error message for both fields**
                if (IDisError) {
                    Text(
                        text = "Invalid Application ID or Work ID. Please try again.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(35.dp))

                // Submit Button
                SubmitButton {
                    if (applicationID == "hello123" && workID == "hello123") {
                        onNavigateToConfirmation() // Navigate to confirmation screen
                    } else {
                        IDisError = true
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Navbar()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationPage(onNavigateToLogin: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.check),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = "Check in confirmed",
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = "You may now go to your work zone and carry out the work required.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp).padding(50.dp, 0.dp)
            )
            Button(
                onClick = { onNavigateToLogin() }, // Navigate back to homepage
                modifier = Modifier.padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = "Go to Homepage", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    AppMain()
}
@Preview(showBackground = true)
@Composable
fun Previewpg() {
    ConfirmationPage {  }
}