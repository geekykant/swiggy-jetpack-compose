package com.example.swiggyapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun SwiggyApp() {
    val context = LocalContext.current
    var isOnline by remember { mutableStateOf(checkIfOnline(context)) }

    if (isOnline){
//        Home()
    }else{
        OfflineDialog { isOnline = checkIfOnline(context)}
    }
}

@Suppress("DEPRECATION")
fun checkIfOnline(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}

@Composable
fun OfflineDialog(onRetry: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        title = { "Connection error" },
        text = { "Unable to reach Swiggy Servers.\nCheck your internet connection and try again." },
        confirmButton = {
            TextButton(onClick = onRetry){
                Text("Retry")
            }
        }
    )
}



