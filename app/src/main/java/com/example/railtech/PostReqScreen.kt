package com.example.railtech
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.material3.Button
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.railtech.models.AccessPoint
//import com.example.railtech.models.WifiScanObject
//import com.example.railtech.network.sendWifiScanData
//import kotlinx.coroutines.launch
//
//@Preview
//@Composable
//fun PostReqTestingScreen() {
//    Surface {
//        Column {
//            val scope = rememberCoroutineScope()
//            Button(onClick = {
//                scope.launch {
//                    val accessPoints = listOf(
//                        AccessPoint("11FYUKGF21324", 10),
//                        AccessPoint("22CFYUGFYRHTR6", 30),
//                        AccessPoint("3389765GHJKMHG", 50)
//                    )
//                    sendWifiScanData(
//                        // Replace with your actual data
//
//                        WifiScanObject(
//                            user = "alonzo",
//                            accessPoints
//                    )
//                    )
//                }
//            }) {
//                Text(text = "Send Request")
//            }
//        }
//    }
//}
