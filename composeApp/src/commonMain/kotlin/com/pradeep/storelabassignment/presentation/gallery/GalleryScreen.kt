package com.pradeep.storelabassignment.presentation.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoSizeSelectLarge
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pradeep.storelabassignment.domain.model.PicsumImage
import com.pradeep.storelabassignment.presentation.gallery.components.ErrorDisplay
import com.pradeep.storelabassignment.presentation.gallery.components.GalleryGrid
import com.pradeep.storelabassignment.presentation.gallery.components.LoadingIndicator
import com.pradeep.storelabassignment.theme.NUM_4
import org.jetbrains.compose.resources.stringResource
import storelabassignment.composeapp.generated.resources.Res
import storelabassignment.composeapp.generated.resources.gallery_title
import storelabassignment.composeapp.generated.resources.sort_by_author
import storelabassignment.composeapp.generated.resources.sort_by_id

/**
 * Gallery Screen - Displays the grid of images and sorting options.
 * This composable is stateless, receiving all data and callbacks from its caller.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    viewModel: GalleryViewModel,
    onImageClick: (PicsumImage) -> Unit = {}
) {
    val state = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.gallery_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { viewModel.toggleSort(SortOption.AUTHOR) }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = stringResource(Res.string.sort_by_author)
                        )
                        Spacer(Modifier.width(NUM_4))
                        Text(stringResource(Res.string.sort_by_author))
                    }
                    TextButton(onClick = { viewModel.toggleSort(SortOption.SIZE) }) {
                        Icon(
                            imageVector = Icons.Default.PhotoSizeSelectLarge,
                            contentDescription = stringResource(Res.string.sort_by_id)
                        )
                        Spacer(Modifier.width(NUM_4))
                        Text(stringResource(Res.string.sort_by_id))
                    }
                }
            }
        }
    ) { paddingValues ->
        GalleryScreenContent(
            modifier = Modifier.padding(paddingValues),
            isLoading = state.isLoading,
            error = state.error,
            images = state.images,
            onImageClick = onImageClick
        )
    }
}

@Composable
fun GalleryScreenContent(
    isLoading: Boolean,
    error: String?,
    images: List<PicsumImage>,
    onImageClick: (PicsumImage) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        when {
            isLoading && images.isEmpty() -> {
                LoadingIndicator()
            }
            error != null -> {
                ErrorDisplay(errorMessage = error)
            }
            else -> {
                GalleryGrid(
                    images = images,
                    onImageClick = onImageClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
