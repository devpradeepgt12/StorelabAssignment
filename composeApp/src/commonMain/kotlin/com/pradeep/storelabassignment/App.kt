package com.pradeep.storelabassignment

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pradeep.storelabassignment.presentation.detail.DetailScreen
import com.pradeep.storelabassignment.presentation.gallery.GalleryScreen
import com.pradeep.storelabassignment.presentation.gallery.GalleryViewModel
import com.pradeep.storelabassignment.theme.AppTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    AppTheme {
        val navController = rememberNavController()
        // The ViewModel is hoisted to the NavHost level, making it shared.
        val galleryViewModel = koinViewModel<GalleryViewModel>()

        Surface {
            NavHost(navController = navController, startDestination = "gallery") {
                composable("gallery") {
                    GalleryScreen(
                        viewModel = galleryViewModel,
                        onImageClick = {
                            // Set the state in the ViewModel and navigate
                            galleryViewModel.onImageSelectedForDetail(it)
                            navController.navigate("detail")
                        }
                    )
                }
                composable("detail") {
                    // The detail screen gets its data directly from the shared ViewModel
                    val image by galleryViewModel.detailImage.collectAsState()
                    if (image != null) {
                        DetailScreen(
                            image = image!!,
                            onBack = {
                                // Clear the state in the ViewModel and navigate back
                                galleryViewModel.onDetailScreenDismissed()
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
