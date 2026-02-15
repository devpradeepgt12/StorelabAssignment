package com.pradeep.storelabassignment

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
        val galleryViewModel = koinViewModel<GalleryViewModel>()

        Surface {
            NavHost(
                navController = navController,
                startDestination = "gallery",
                enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300)) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300)) },
                popEnterTransition = { fadeIn(animationSpec = tween(300)) },
                popExitTransition = { fadeOut(animationSpec = tween(300)) }
            ) {
                composable("gallery") {
                    GalleryScreen(
                        viewModel = galleryViewModel,
                        onImageClick = {
                            galleryViewModel.onImageSelectedForDetail(it)
                            navController.navigate("detail")
                        }
                    )
                }
                composable("detail") {
                    val image by galleryViewModel.detailImage.collectAsState()
                    if (image != null) {
                        DetailScreen(
                            image = image!!,
                            onBack = {
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
