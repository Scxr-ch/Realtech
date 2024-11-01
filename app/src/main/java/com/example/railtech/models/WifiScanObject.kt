package com.example.railtech.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


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

@Parcelize
data class Circle(
    val x: Float,
    val y: Float,
    val radius: Float
) : Parcelable

@Parcelize
data class Person(
    val current_coordinates: Circle,
    val previous_coordinates: Circle,
    val name: String,
    val radius: Float,
    val rssi: @RawValue List<AccessPoint>,
    val tracking: Boolean
//    val email: String,
//    val job: String,
) : Parcelable

@Parcelize
data class Coordinates(
    val x: Float,
    val y: Float
) : Parcelable
@Parcelize
data class WorkzoneRectangle(
    val label: String = "",
    val rectLeftX: Float,
    val rectBottomY: Float,
    val rectWidth: Float,
    val rectHeight: Float
) : Parcelable

@Parcelize
data class AccessPointLocation(
    val mac: String,
    val radius: Float,
    val location: @RawValue Coordinates,
    val trilat: @RawValue List<Circle>
) : Parcelable

@Parcelize
data class WifiScanResponse(
    val persons: @RawValue List<Person> = emptyList(),
    val accessPoints:  @RawValue List<AccessPointLocation> = emptyList(),
    val workzones: @RawValue List<WorkzoneRectangle> = emptyList()
) : Parcelable

fun WifiScanResponse.flipAxes(): WifiScanResponse {
    // Flip persons' coordinates
    val flippedPersons = persons.map { person ->
        person.copy(
            current_coordinates = person.current_coordinates.flip(),
            previous_coordinates = person.previous_coordinates.flip()
        )
    }

    // Flip access points' locations and trilateration circles
    val flippedAccessPoints = accessPoints.map { apLocation ->
        apLocation.copy(
            location = apLocation.location.flip(),
            trilat = apLocation.trilat.map { it.flip() }
        )
    }

    // Flip workzones by swapping both position and size dimensions
    val flippedWorkzones = workzones.map { workzone ->
        workzone.copy(
            rectLeftX = workzone.rectBottomY,
            rectBottomY = workzone.rectLeftX,
            rectWidth = workzone.rectHeight,
            rectHeight = workzone.rectWidth
        )
    }

    return this.copy(
        persons = flippedPersons,
        accessPoints = flippedAccessPoints,
        workzones = flippedWorkzones
    )
}

// Extension function to flip Coordinates
fun Coordinates.flip() = this.copy(x = this.y, y = this.x)

// Extension function to flip Circle
fun Circle.flip() = this.copy(x = this.y, y = this.x)