package com.example.railtech.models


data class WifiScanObject(
    val user: String,
    val accessPoints: List<AccessPoint>
)


data class StepsInfoObject(
    val user: String,
    val directions: List<String>,
    val steps: Int
)

data class CheckInObject(
    val user: String,
    val checkIn: Boolean,
    val sessionID: String
)

data class AccessPoint (
    val ssid: String,
    val bssid: String,
    val signalStrength: Int,
    val frequency: Int
)

data class Circle(
    val x: Float,
    val y: Float,
    val radius: Float
)

data class Person(
    val current_coordinates: Circle,
    val previous_coordinates: Circle,
    val name: String,
    val radius: Float,
    val rssi: List<AccessPoint>,
    val tracking: Boolean
//    val email: String,
//    val job: String,
)

data class Coordinates(
    val x: Float,
    val y: Float
)

data class WorkzoneRectangle(
    val label: String,
    val rectLeftX: Float,
    val rectBottomY: Float,
    val rectWidth: Float,
    val rectHeight: Float
)

data class AccessPointLocation(
    val mac: String,
    val radius: Float,
    val location: Coordinates,
    val trilat: List<Circle>
)

data class WifiScanResponse(
    val persons: List<Person>,
    val accessPoints: List<AccessPointLocation>,
    val workzones: List<WorkzoneRectangle>
)