package com.example.railtech
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService

// THIS IS NOT SHOWN ANYMORE
@Composable
fun pop_up() {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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



@Composable
fun Alert_screen(
    onDismiss: () -> Unit,
) {val context = LocalContext.current
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val notificationId = 2 // Replace with your notification's ID

    // Cancel the notification

    AlertDialog(
        modifier = Modifier.height(400.dp),
        containerColor = Color.Red,
        onDismissRequest = {  },
        confirmButton = {
            Button(
                onClick = {onDismiss()
                    notificationManager.cancel(2)
                }
                ,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,  // Set button background to white
                    contentColor = Color.Red       // Set button text color to red
                )
            ) {
                Text(text = "Confirm")
            }
        },
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally // Center align contents horizontally
                ) {
                    Text(
                        text = "Please Return to your designated workzone",
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = "You are in workzone xxx instead of the one assigned to you (xxx).",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                        text = "This popup will not close until you return to your workzone.",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        },
    )
}
@Preview
@Composable
fun preview() {
    pop_up()
}
