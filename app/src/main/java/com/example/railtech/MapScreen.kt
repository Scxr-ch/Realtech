package com.example.railtech

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.railtech.ui.theme.Navbar
import com.example.railtech.ui.theme.NavbarComposable

@Composable
fun MapScreen(onNavigateToCheckout: () -> Unit) {
    Scaffold(
        bottomBar = {
            NavbarComposable()
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
//            GridCanvas(modifier = Modifier.size(200.dp).background(Color.LightGray))

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Apply background color to check if canvas is visible
                val points = listOf(
                    Pair(2, 2),
                    Pair(5, 5),
                    Pair(8, 3)
                )
                GridCanvas(gridPoints = points, modifier = Modifier.size(200.dp).background(Color.LightGray))
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
    modifier: Modifier = Modifier,
    gridSize: Dp = 20.dp,
    gridPoints: List<Pair<Int, Int>> = emptyList(),
    pointColor: Color = Color.Red,
    canvasWidth: Dp = 350.dp,
    canvasHeight: Dp = 700.dp,
    pointRadius: Float = 5f
) {
    Box(modifier = Modifier.size(canvasWidth, canvasHeight)) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val cellSize = gridSize.toPx()
            drawGrid(gridSize = cellSize, color = Color.Gray)
            drawGridPoints(gridPoints, cellSize, color = pointColor, radius = pointRadius)
        }
    }
}

fun DrawScope.drawGrid(gridSize: Float, color: Color) {
    val width = size.width
    val height = size.height

    for (x in 0..(width / gridSize).toInt()) {
        val startX = x * gridSize
        drawLine(
            color = color,
            start = Offset(startX, 0f),
            end = Offset(startX, height)
        )
    }

    for (y in 0..(height / gridSize).toInt()) {
        val startY = y * gridSize
        drawLine(
            color = color,
            start = Offset(0f, startY),
            end = Offset(width, startY)
        )
    }
}

fun DrawScope.drawGridPoints(
    gridPoints: List<Pair<Int, Int>>,
    cellSize: Float,
    color: Color,
    radius: Float
) {
    gridPoints.forEach { (x, y) ->
        val pixelOffset = Offset(x * cellSize + cellSize / 2, y * cellSize + cellSize / 2)
        drawCircle(
            color = color,
            radius = radius,
            center = pixelOffset
        )
    }
}

//
//@Composable
//fun GridCanvas(gridSize: Dp = 20.dp,
//               canvasWidth: Dp = 350.dp,
//               canvasHeight: Dp = 700.dp,
//               gridPoints: List<Pair<Int, Int>> = emptyList(),
//               pointColor: Color = Color.Red,
//               pointRadius: Float = 10f,
//               modifier: Modifier) {
//    Box {
//        Canvas(modifier = modifier) {
//            val cellSize = gridSize.toPx()
//            drawGrid(gridSize = cellSize, color = Color.Gray)
//            drawGridPoints(gridPoints, cellSize, color = pointColor, radius = pointRadius)
//        }
//    }
//
//
//}
//
//fun DrawScope.drawGrid(gridSize: Float, color: Color) {
//    val width = size.width
//    val height = size.height
//
//    for (x in 0..(width / gridSize).toInt()) {
//        val startX = x * gridSize
//        drawLine(
//            color = color,
//            start = Offset(startX, 0f),
//            end = Offset(startX, height)
//        )
//    }
//
//    for (y in 0..(height / gridSize).toInt()) {
//        val startY = y * gridSize
//        drawLine(
//            color = color,
//            start = Offset(0f, startY),
//            end = Offset(width, startY)
//        )
//    }
//}
//
//fun DrawScope.drawGridPoints(
//    gridPoints: List<Pair<Int, Int>>,
//    cellSize: Float,
//    color: Color,
//    radius: Float
//) {
//    gridPoints.forEach { (x, y) ->
//        val pixelOffset = Offset(x * cellSize + cellSize / 2, y * cellSize + cellSize / 2)
//        drawCircle(
//            color = color,
//            radius = radius,
//            center = pixelOffset
//        )
//    }
//}



@Preview(showBackground = true)
@Composable
fun PreviewMapScreen() {
    MapScreen(onNavigateToCheckout = {})
}


//
//@Preview(showBackground = true)
//@Composable
//fun PreviewGrid() {
//    GridCanvas()
//}