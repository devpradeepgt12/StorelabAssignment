package com.pradeep.storelabassignment.presentation.gallery

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.pradeep.storelabassignment.domain.model.PicsumImage
import com.pradeep.storelabassignment.presentation.gallery.components.ErrorDisplay
import com.pradeep.storelabassignment.presentation.gallery.components.GalleryGrid
import com.pradeep.storelabassignment.presentation.gallery.components.GalleryItemCard
import com.pradeep.storelabassignment.presentation.gallery.components.LoadingIndicator

/**
 * Preview: Single gallery item card
 * - Test with one image
 * - Verify design and clickability
 */
@Preview(showBackground = true)
@Composable
fun PreviewGalleryItemCard() {
    val sampleImage = PicsumImage(
        id = "1",
        author = "John Doe",
        width = 400,
        height = 400,
        url = "https://picsum.photos/400/400?random=1",
        downloadUrl = "https://picsum.photos/400/400?random=1"
    )

    GalleryItemCard(
        image = sampleImage,
        onClick = { }
    )
}

/**
 * Preview: Loading indicator
 * - Shows centered circular progress
 * - Test loading state UI
 */
@Preview(showBackground = true)
@Composable
fun PreviewLoadingIndicator() {
    LoadingIndicator()
}

/**
 * Preview: Error display
 * - Shows error message centered
 * - Test error state UI
 */
@Preview(showBackground = true)
@Composable
fun PreviewErrorDisplay() {
    ErrorDisplay(errorMessage = "Failed to load images. Please check your connection.")
}

/**
 * Preview: Gallery grid with sample data
 * - Shows grid of multiple items
 * - Test grid layout and scrolling
 */
@Preview(showBackground = true)
@Composable
fun PreviewGalleryGrid() {
    val sampleImages = listOf(
        PicsumImage("1", "Alice Johnson", 400, 400, "https://picsum.photos/400/400?random=1", "https://picsum.photos/400/400?random=1"),
        PicsumImage("2", "Bob Smith", 400, 400, "https://picsum.photos/400/400?random=2", "https://picsum.photos/400/400?random=2"),
        PicsumImage("3", "Charlie Brown", 400, 400, "https://picsum.photos/400/400?random=3", "https://picsum.photos/400/400?random=3"),
        PicsumImage("4", "Diana Prince", 400, 400, "https://picsum.photos/400/400?random=4", "https://picsum.photos/400/400?random=4"),
        PicsumImage("5", "Eve Wilson", 400, 400, "https://picsum.photos/400/400?random=5", "https://picsum.photos/400/400?random=5"),
        PicsumImage("6", "Frank Miller", 400, 400, "https://picsum.photos/400/400?random=6", "https://picsum.photos/400/400?random=6"),
    )

    GalleryGrid(
        images = sampleImages,
        onImageClick = { }
    )
}

// ==================== SCREEN LEVEL PREVIEWS ====================

/**
 * Preview: Gallery Screen - Loading State
 * - Full screen loading indicator
 * - Test complete loading UI
 */
@Preview(showBackground = true)
@Composable
fun PreviewGalleryScreenLoading() {
    GalleryScreenContent(
        isLoading = true,
        error = null,
        images = emptyList(),
        onImageClick = { }
    )
}

/**
 * Preview: Gallery Screen - Error State
 * - Full screen error message
 * - Test complete error UI
 */
@Preview(showBackground = true)
@Composable
fun PreviewGalleryScreenError() {
    GalleryScreenContent(
        isLoading = false,
        error = "Network error: Unable to fetch images",
        images = emptyList(),
        onImageClick = { }
    )
}

/**
 * Preview: Gallery Screen - Success State
 * - Full screen with gallery grid and sort buttons
 * - Test complete successful UI
 */
@Preview(showBackground = true)
@Composable
fun PreviewGalleryScreenSuccess() {
    val sampleImages = listOf(
        PicsumImage("1", "Alice Johnson", 400, 400, "https://picsum.photos/400/400?random=1", "https://picsum.photos/400/400?random=1"),
        PicsumImage("2", "Bob Smith", 400, 400, "https://picsum.photos/400/400?random=2", "https://picsum.photos/400/400?random=2"),
        PicsumImage("3", "Charlie Brown", 400, 400, "https://picsum.photos/400/400?random=3", "https://picsum.photos/400/400?random=3"),
        PicsumImage("4", "Diana Prince", 400, 400, "https://picsum.photos/400/400?random=4", "https://picsum.photos/400/400?random=4"),
    )

    GalleryScreenContent(
        isLoading = false,
        error = null,
        images = sampleImages,
        onImageClick = { }
    )
}

/**
 * Preview: Gallery Screen - Empty State
 * - No loading, no error, empty gallery
 * - Test empty state UI
 */
@Preview(showBackground = true)
@Composable
fun PreviewGalleryScreenEmpty() {
    GalleryScreenContent(
        isLoading = false,
        error = null,
        images = emptyList(),
        onImageClick = { }
    )
}
