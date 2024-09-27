package com.example.railtech.ui.theme

import android.media.Image
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.railtech.R
import com.example.railtech.ui.theme.RailtechTheme
import coil.compose.rememberAsyncImagePainter


//import com.example.myapplication.App.login_screen


@Composable
fun picture(
    modifier: Modifier = Modifier,
   defaultImage: Painter = painterResource(id = R.drawable.imges),
    onImageSelected: (Uri) -> Unit
)
{
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    //Image picker

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if(uri != null){
            selectedImageUri = uri
            onImageSelected(uri)// notify parent abt selected img
        }

    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(150.dp) // Size of the profile picture
            .clip(CircleShape)
            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            .clickable {
                // Trigger the image picker
                launcher.launch("image/*")
            }
    ) {
        if (selectedImageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(model = selectedImageUri),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Show the default image if none is selected
            Image(
                painter = defaultImage,
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

    }
}
@Composable
fun profile (modifier : Modifier = Modifier)
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        //Text(text = "Edit Profile Picture", fontSize = 20.sp)

        // Editable profile picture
        picture(
            onImageSelected = { uri ->
                // Handle the image selection
                // You could save the URI to a view model, display it elsewhere, etc.
                println("Selected image URI: $uri")
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Additional UI for the profile screen
        Text(
            text = "User Name",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 40.sp,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Relevant Information"
        )
    }
}




@Preview(showBackground = true)
@Composable
fun profilePreview() {
    RailtechTheme  {
        profile()

    }
}