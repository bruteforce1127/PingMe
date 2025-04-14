package com.example.pingme

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.pingme.Location.LocationService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // This single function call does all the setup:
                // permission checking and wiring up start/stop service buttons.
                SetupLocationServiceControls()
            }
        }
    }
}

@Composable
fun SetupLocationServiceControls() {
    val context = LocalContext.current

    // Define the permissions needed (location and, for API 33+ notification)
    val permissions = mutableListOf(Manifest.permission.ACCESS_FINE_LOCATION)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissions.add(Manifest.permission.POST_NOTIFICATIONS)
    }

    // Create a launcher to request permissions in Compose
    val permissionLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { /* You can handle the result here if needed */ }
    )

    // Request any missing permissions on launch
    LaunchedEffect(Unit) {
        val neededPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
        }
        if (neededPermissions.isNotEmpty()) {
            permissionLauncher.launch(neededPermissions.toTypedArray())
        }
    }

    // Compose UI: Two buttons to start and stop your LocationService.
    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = {
                val intent = Intent(context, LocationService::class.java)
                // For Android O (API 26) and above, use startForegroundService
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent)
                } else {
                    context.startService(intent)
                }



            },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text("Start Service")
        }
        Button(
            onClick = {
                val intent = Intent(context, LocationService::class.java)
                context.stopService(intent)
            }
        ) {
            Text("Stop Service")
        }
    }

}
