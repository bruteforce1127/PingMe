package com.example.pingme.Notification

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

fun sendNotification(context: Context, title: String, message: String) {
    val channelId = "default_channel_id" // Must match the channel you created
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_info)  // Replace with your app icon
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)  // Automatically removes the notification when tapped

    // Check for notification permission on Android 13+ (API 33)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) ==
            PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(context).notify(1, builder.build())
        } else {
            Log.e("Notification", "Notification permission not granted.")
            // Optionally, request permission or handle the lack of permission gracefully.
        }
    } else {
        // For Android versions lower than 33, runtime permission isn't required.
        NotificationManagerCompat.from(context).notify(1, builder.build())
    }
}
