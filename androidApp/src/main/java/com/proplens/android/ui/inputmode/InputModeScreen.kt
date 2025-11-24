package com.proplens.android.ui.inputmode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun InputModeScreen(
    onManualSelected: () -> Unit,
    onScreenshotSelected: () -> Unit,
    onUrlSelected: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Add a Deal",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onManualSelected, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Enter Manually")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onScreenshotSelected, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Use Screenshot (stub)")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onUrlSelected, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Use Listing URL (stub)")
            }
        }
    }
}
