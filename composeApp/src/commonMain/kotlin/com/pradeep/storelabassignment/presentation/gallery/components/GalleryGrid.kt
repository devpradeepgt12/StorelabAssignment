package com.pradeep.storelabassignment.presentation.gallery.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.pradeep.storelabassignment.domain.model.PicsumImage
import com.pradeep.storelabassignment.presentation.gallery.SortOption
import com.pradeep.storelabassignment.theme.NUM_16
import com.pradeep.storelabassignment.theme.NUM_1F
import com.pradeep.storelabassignment.theme.NUM_8
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

/**
 * Atomic component: Gallery grid that displays images
 * - Stateless - all data passed as parameters (state hoisting)
 * - Reusable in multiple contexts
 */
@Composable
fun GalleryGrid(
    images: List<PicsumImage>,
    onImageClick: (PicsumImage) -> Unit,
    loadMore: () -> Unit,
    isLoadingNextPage: Boolean,
    sortOption: SortOption,
    modifier: Modifier = Modifier
) {
    val lazyGridState = rememberLazyGridState()

    // This is the definitive, robust way to handle pagination triggers in Compose.
    // It creates a reactive Flow from the scroll state.
    LaunchedEffect(lazyGridState) {
        snapshotFlow { lazyGridState.layoutInfo }
            .map { layoutInfo ->
                // The condition to load more is when the last visible item's index
                // is close to the total number of items.
                val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                if (lastVisibleItem == null) {
                    false
                } else {
                    // Trigger when 5 items are left to show.
                    lastVisibleItem.index >= layoutInfo.totalItemsCount - 5
                }
            }
            .distinctUntilChanged() // Only emit when the value changes from false to true.
            .filter { it } // Only react when the condition is true.
            .collect { 
                loadMore()
            }
    }

    // This effect will scroll the grid to the top whenever the sort option changes.
    LaunchedEffect(sortOption) {
        lazyGridState.scrollToItem(0)
    }

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Fixed(3),
        modifier = modifier.padding(horizontal = NUM_8),
        horizontalArrangement = Arrangement.spacedBy(NUM_8),
        verticalArrangement = Arrangement.spacedBy(NUM_8)
    ) {
        items(images, key = { it.id }) {
            ImageItem(image = it, onClick = { onImageClick(it) })
        }

        // The footer item MUST span all columns to be visible and to ensure
        // correct layout calculation for the pagination trigger.
        item(span = { GridItemSpan(maxLineSpan) }) {
            if (isLoadingNextPage) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = NUM_16),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun ImageItem(image: PicsumImage, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(NUM_1F)
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = image.downloadUrl,
            contentDescription = image.author,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
