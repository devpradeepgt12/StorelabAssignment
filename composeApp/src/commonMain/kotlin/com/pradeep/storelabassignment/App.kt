package com.pradeep.storelabassignment

import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.pradeep.storelabassignment.presentation.detail.DetailScreen
import com.pradeep.storelabassignment.presentation.gallery.GalleryScreen
import com.pradeep.storelabassignment.presentation.gallery.GalleryViewModel
import com.pradeep.storelabassignment.theme.AppTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    AppTheme {
        // The ViewModel is created here and owned by the App composable.
        val galleryViewModel = koinViewModel<GalleryViewModel>()
        val state by galleryViewModel.uiState.collectAsState()

        Surface {
            // Simple Navigation Logic based on the ViewModel's state
            if (state.selectedImage != null) {
                // Detail View
                DetailScreen(
                    image = state.selectedImage!!,
                    onBack = { galleryViewModel.selectImage(null) }
                )
            } else {
                // The single ViewModel instance is passed down to the GalleryScreen.
                GalleryScreen(
                    viewModel = galleryViewModel,
                    onImageClick = { galleryViewModel.selectImage(it) }
                )
            }
        }
    }
}
