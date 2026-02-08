package com.pradeep.storelabassignment.presentation.gallery.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Atomic component: Error state display
 * - Stateless - receives error message as parameter
 * - Centered on screen
 */
@Composable
fun ErrorDisplay(
    errorMessage: String?,
    modifier: Modifier = Modifier
) {
    if (errorMessage != null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Error: $errorMessage")
        }
    }
}

