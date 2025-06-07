package com.fernandort.recuerda.utilities.dispositivo

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Para hacer llamadas telefónicas, hay que añadir en el manifest:
// <uses-feature
//      android:name="android.hardware.telephony"
//      android:required="true" />
// <uses-permission android:name="android.permission.CALL_PHONE"/>
// Uso:
//      telefono.launch(android.Manifest.permission.CALL_PHONE)
@Composable
fun registroLlamarPorTelefonoIntent(
    telefono: String
): ManagedActivityResultLauncher<String, Boolean> {
    val context = LocalContext.current
    return rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { success ->
        if (success) {
            Intent(Intent.ACTION_CALL).also { callIntent ->
                callIntent.data = Uri.parse("tel:$telefono")
                context.startActivity(callIntent)
            }
        }
    }
}

@Composable
fun notificationPermissionLauncher(): ManagedActivityResultLauncher<String, Boolean> {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("NotificationPermission", "Permission granted")
        } else {
            Log.d("NotificationPermission", "Permission denied")
        }
    }

    return launcher
}