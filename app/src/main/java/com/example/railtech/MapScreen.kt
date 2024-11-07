package com.example.railtech

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.text.color
import com.example.railtech.models.AccessPoint
import com.example.railtech.models.Circle
import com.example.railtech.models.Coordinates
import com.example.railtech.models.Person
import com.example.railtech.models.UserCoordinates
import com.example.railtech.models.WifiScanResponse
import com.example.railtech.models.WorkzoneRectangle
import com.example.railtech.models.flipAxes
import com.example.railtech.ui.theme.NavbarComposable
import com.google.gson.Gson

@Composable
fun MapScreen(onNavigateToCheckout: () -> Unit, onClickMap: () -> Unit, onClickGPS: () -> Unit) {
    val context = LocalContext.current
    val receivedData = remember { mutableStateOf<WifiScanResponse?>(null) } // Store received data

    // inWorkzone functionality
    var showDialog by remember { mutableStateOf(false) }
    fun showDialog() {

        showDialog = true
        Log.d("NotificationTest", "Button clicked, attempting to show notification")
        val builder = NotificationCompat.Builder(context, "workzones_channel")
            .setSmallIcon(R.drawable.workzone_notice_icon)
            .setContentTitle("Workzones notice")
            .setContentText("Please return back to your dedicated workzone!")
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(2, builder.build())

    }


    // Register the BroadcastReceiver
    val receiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val data = intent?.getParcelableExtra<WifiScanResponse>("response_key")
                if (data != null) {
                    // Update your UI or state with the received data
                    Log.d("BroadcastReceiver", "Received response: $data")
                    receivedData.value = data.flipAxes()
                    receivedData.value!!.inWorkzones.forEach { inWorkzone ->
                        val (workzone, people) = inWorkzone

                        people.forEach { person ->
                            if (person == User.name) {
                                if (!receivedData.value!!.correctWorkzone.any { it == workzone }) {

                                    showDialog()
                                }
                            }
                        }

                    }
                }
            }
        }
    }


    DisposableEffect(Unit) {
        val filter = IntentFilter("com.example.UPDATE_UI")
        context.registerReceiver(receiver, filter)

        // Cleanup: Unregister the receiver when the composable leaves the composition
        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    Scaffold(
        bottomBar = {
            NavbarComposable(
                onClickMap = onClickMap,
                onClickGPS = onClickGPS,
                selectMap = true,
                selectGPS = false
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (showDialog) {
                    Alert_screen(onDismiss = { showDialog = false })
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Generate dummy Circles for current and previous coordinates
                val circle1 = Circle(-6.0f, 19.0f, 5.0f)
                val circle2 = Circle(1f, 15.0f, 4.0f)

//                // Create a list of Persons with dummy data
//                val persons = listOf(
//                    Person(circle1, circle2, "Alice", 5.0f, accessPoints1, tracking = true),
//                    Person(circle2, circle2, "Bob", 4.0f, accessPoints2, tracking = false),
//                    Person(Circle(7.0f, 10.0f, 3.0f), Circle(22.0f, 32.0f, 3.5f), "Charlie", 4.5f, accessPoints1, tracking = true),
//                    Person(Circle(1.0f, 3.0f, 2.5f), Circle(26.0f, 36.0f, 2.0f), "Dana", 3.5f, accessPoints2, tracking = false),
//                    Person(Circle(3.0f, 10.0f, 5.5f), Circle(28.0f, 38.0f, 4.5f), "Eve", 5.0f, accessPoints1, tracking = true)
//                )
//
//                val rectangles = listOf(
//                    WorkzoneRectangle("Workzone A", rectLeftX = 5f, rectBottomY = 13f, rectWidth = 2f, rectHeight = 2f),
//                    WorkzoneRectangle("Workzone B", rectLeftX = 2f, rectBottomY = 4f, rectWidth = 2f, rectHeight = 1f)
//                )

                val persons = receivedData.value?.Users
//                val persons = receivedData.value?.persons
                val rectangles = receivedData.value?.workzones
//                val accessPoints = receivedData.value?.accessPoints


                if (persons != null && rectangles != null) {
                    GridCanvas(people = persons, rectangles = rectangles)
                } else {
                    Log.d("Debug", "Received data is null NAHUFJDGHFGSJGHMGFGEJJ")
                    val accessPoints1 = listOf(
                        AccessPoint("SSID_1", "BSSID_1", -50, 2400),
                        AccessPoint("SSID_2", "BSSID_2", -70, 5200)
                    )

                    val accessPoints2 = listOf(
                        AccessPoint("SSID_3", "BSSID_3", -65, 2400),
                        AccessPoint("SSID_4", "BSSID_4", -80, 5200)
                    )

                    val persons = listOf(
                        Person(circle1, circle2, "Alice", 5.0f, accessPoints1, tracking = true),
                        Person(circle2, circle2, "Bob", 4.0f, accessPoints2, tracking = false),
                        Person(Circle(7.0f, 10.0f, 3.0f), Circle(22.0f, 32.0f, 3.5f), "Charlie", 4.5f, accessPoints1, tracking = true),
                        Person(Circle(1.0f, 3.0f, 2.5f), Circle(26.0f, 36.0f, 2.0f), "Dana", 3.5f, accessPoints2, tracking = false),
                        Person(Circle(3.0f, 10.0f, 5.5f), Circle(28.0f, 38.0f, 4.5f), "Eve", 5.0f, accessPoints1, tracking = true)
                    )

                    val rectangles = listOf(
                        WorkzoneRectangle("Workzone A", rectLeftX = 5f, rectBottomY = 13f, rectWidth = 2f, rectHeight = 2f),
                        WorkzoneRectangle("Workzone B", rectLeftX = 2f, rectBottomY = 4f, rectWidth = 2f, rectHeight = 1f)
                    )
                }
                ElevatedButton(onClick = onNavigateToCheckout) {
                    Text(
                        text = "Check out",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}



@Composable
fun GridCanvas(
//    modifier: Modifier = Modifier,
    gridSize: Dp = 15.dp,
    rectangles: List<WorkzoneRectangle> = emptyList(),
    pointColor: Color = Color.Red,
//    minX: Int = 0,
//    maxX: Int = 25,
//    minY: Int = -7,
//    maxY: Int = 7,
    minX: Int = -1,
    maxX: Int = 20,
    minY: Int = -2,
    maxY: Int = 25,
    people: List<UserCoordinates> = emptyList(),
    pointRadius: Float = 10f
) {
// Calculate the number of cells based on the coordinate range
    val numSquaresX = maxX - minX
    val numSquaresY = maxY - minY

    // Calculate canvas width and height based on the number of cells and grid size
    val canvasWidth = gridSize * numSquaresX
    val canvasHeight = gridSize * numSquaresY

//    Box(modifier = Modifier.size(canvasWidth.toDp(), canvasHeight.toDp())) {
    Box(modifier = Modifier.size(canvasWidth, canvasHeight)) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val cellSize = gridSize.toPx()
            val centerX = maxX * cellSize // Offset to align `minX` at the left of the canvas
            val centerY = maxY * cellSize // Offset to align `minY` at the bottom of the canvas

            // Draw grid with the specified min/max range
            drawGraphGrid(cellSize, color = Color.LightGray, minX, maxX, minY, maxY, centerX, centerY)
            drawRectangles(rectangles, cellSize, rectColor = Color(0x80ADD8E6), centerX = centerX, centerY = centerY)
            drawPeoplePoints(people, cellSize, color = pointColor, radius = pointRadius, minX, maxX, minY, maxY)
            drawAxesLabels(cellSize, minX, maxX, minY, maxY, centerX, centerY)
        }
    }
}

fun DrawScope.drawRectangles(
    rectangles: List<WorkzoneRectangle>,
    cellSize: Float,
    rectColor: Color,
    labelColor: Int = android.graphics.Color.BLACK,
    centerX: Float = 0f,
    centerY: Float = 0f
) {
    // Configure paint for text labels
    val textPaint = Paint().apply {
        color = labelColor
        textSize = cellSize / 2
        isAntiAlias = true
    }

    // Draw each rectangle and its label
    rectangles.forEach { rect ->
        // Calculate the rectangle’s top-left position in pixels based on cell size and grid offset
        val topLeft = Offset(
            x = centerX + rect.rectLeftX * cellSize,
            y = centerY - (rect.rectBottomY + rect.rectHeight) * cellSize
        )

        // Define the rectangle's size in pixels
        val size = androidx.compose.ui.geometry.Size(
            width = rect.rectWidth * cellSize,
            height = rect.rectHeight * cellSize
        )

        // Draw the rectangle with specified color
        drawRect(
            color = rectColor,
            topLeft = topLeft,
            size = size
        )

        // Draw the label centered near the rectangle
        drawContext.canvas.nativeCanvas.drawText(
            rect.label,
            topLeft.x + size.width / 2 - textPaint.textSize / 4,
            topLeft.y + size.height / 2 + textPaint.textSize / 4,
            textPaint
        )
    }
}


fun DrawScope.drawGraphGrid(
    cellSize: Float,
    color: Color,
    minX: Int,
    maxX: Int,
    minY: Int,
    maxY: Int,
    centerX: Float,
    centerY: Float
) {
    // Draw vertical lines for the x-axis grid
    for (x in minX..maxX) {
        val startX = centerX + x * cellSize
        drawLine(
            color = color,
            start = Offset(startX, 0f),
            end = Offset(startX, size.height),
            strokeWidth = 3f
        )
    }

    // Draw horizontal lines for the y-axis grid
    for (y in minY..maxY) {
        val startY = centerY - (y * cellSize) // Invert y coordinate for drawing
        drawLine(
            color = color,
            start = Offset(0f, startY),
            end = Offset(size.width, startY),
            strokeWidth = 3f
        )
    }
}


fun DrawScope.drawAxesLabels(cellSize: Float, minX: Int, maxX: Int, minY: Int, maxY: Int, centerX: Float, centerY: Float) {
    val paint = Paint().apply {
        color = android.graphics.Color.BLACK
        textSize = cellSize / 2
    }

    val width = size.width
    val height = size.height

    // Draw x-axis labels
    for (x in minX..maxX) {
        val label = x.toString()
        val xOffset = centerX + (x * cellSize) + cellSize / 4 // Offset to center label
        drawContext.canvas.nativeCanvas.drawText(label, xOffset, height - (cellSize / 4), paint) // Positioning near bottom
    }

    // Draw y-axis labels
    for (y in minY..maxY) {
        val label = y.toString()
        val yOffset = centerY - (y * cellSize) + cellSize / 4 // Inverted for proper positioning
        drawContext.canvas.nativeCanvas.drawText(label, cellSize / 4, yOffset, paint) // Positioning near left
    }
}

fun DrawScope.drawPeoplePoints(
    people: List<UserCoordinates>,
    cellSize: Float,
    color: Color,
    radius: Float,
    minX: Int,
    maxX: Int,
    minY: Int,
    maxY: Int
) {
    // Calculate the center offset based on min and max values
    val centerX = -minX * cellSize // Shifts origin for x-axis
    val centerY = size.height + (minY * cellSize) // Shifts origin for y-axis

    val paint = Paint().apply {
        textSize = cellSize / 2
    }
    paint.color = android.graphics.Color.BLACK

    people.forEach { person ->
        val (x, y) = person

        // Convert coordinates to canvas pixel offsets
        val pixelOffset = Offset(centerX + x * cellSize, centerY - y * cellSize)

        // Draw the point
        drawCircle(
            color = color,
            radius = radius,
            center = pixelOffset
        )

        // Draw the label slightly offset from the point
        person.name?.let {
            drawContext.canvas.nativeCanvas.drawText(
                it,
                pixelOffset.x + radius * 2,
                pixelOffset.y - radius * 2,
                paint
            )
        }
    }
    /*
    val paint = Paint().apply {
        textSize = cellSize / 2
    }
    paint.color = android.graphics.Color.BLACK

//    val centerX = size.width / 2
//    val centerY = size.height / 2

    people.forEach { person ->
//        val (x, y) = person.current_coordinates
        val x = person.current_coordinates.x
        val y = person.current_coordinates.y

        // Calculate the pixel position on the Cartesian plane
        val pixelOffset = Offset(centerX + x * cellSize, centerY - y * cellSize) // Invert y-axis

        // Draw the point
        drawCircle(
            color = color,
            radius = radius,
            center = pixelOffset
        )

        // Draw the label slightly offset from the point
        drawContext.canvas.nativeCanvas.drawText(
            person.name,
            pixelOffset.x + radius * 2,
            pixelOffset.y - radius * 2,
            paint
        )
    }
    */

}
@Preview(showBackground = true)
@Composable
fun PreviewMapScreen() {
    MapScreen(onNavigateToCheckout = {}, onClickMap = {}, onClickGPS = {})
}


//
//@Preview(showBackground = true)
//@Composable
//fun PreviewGrid() {
//    GridCanvas()
//}