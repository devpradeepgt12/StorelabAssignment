package com.pradeep.storelabassignment.presentation.gallery.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pradeep.storelabassignment.domain.model.PicsumImage
import com.pradeep.storelabassignment.theme.NUM_128

/**
 * Atomic component: Gallery grid that displays images
 * - Stateless - all data passed as parameters (state hoisting)
 * - Reusable in multiple contexts
 */
@Composable
fun GalleryGrid(
    images: List<PicsumImage>,
    onImageClick: (PicsumImage) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = NUM_128),
        modifier = modifier
    ) {
        items(images.size) { index ->
            val image = images[index]
            GalleryItemCard(
                image = image,
                onClick = { onImageClick(image) }
            )
        }
    }
}
