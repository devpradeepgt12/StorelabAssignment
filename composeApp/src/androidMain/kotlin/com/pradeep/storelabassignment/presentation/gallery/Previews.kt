package com.pradeep.storelabassignment.presentation.gallery

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pradeep.storelabassignment.domain.model.PicsumImage
import com.pradeep.storelabassignment.presentation.gallery.components.ErrorDisplay
import com.pradeep.storelabassignment.presentation.gallery.components.GalleryGrid
import com.pradeep.storelabassignment.presentation.gallery.components.LoadingIndicator

// Sample data for previews
val sampleImages = listOf(
    PicsumImage("1", "Alice Johnson", 400, 400, "https://picsum.photos/400/400?random=1", "https://picsum.photos/400/400?random=1"),
    PicsumImage("2", "Bob Smith", 400, 400, "https://picsum.photos/400/400?random=2", "https://picsum.photos/400/400?random=2"),
    PicsumImage("3", "Charlie Brown", 400, 400, "https://picsum.photos/400/400?random=3", "https://picsum.photos/400/400?random=3"),
    PicsumImage("4", "Diana Prince", 400, 400, "https://picsum.photos/400/400?random=4", "https://picsum.photos/400/400?random=4"),
    PicsumImage("5", "Eve Wilson", 400, 400, "https://picsum.photos/400/400?random=5", "https://picsum.photos/400/400?random=5"),
    PicsumImage("6", "Frank Miller", 400, 400, "https://picsum.photos/400/400?random=6", "https://picsum.photos/400/400?random=6"),
)

/**
 * Preview: Loading indicator
 */
@Preview(showBackground = true)
@Composable
fun PreviewLoadingIndicator() {
    LoadingIndicator()
}

/**
 * Preview: Error display
 */
@Preview(showBackground = true)
@Composable
fun PreviewErrorDisplay() {
    ErrorDisplay(errorMessage = "Failed to load images. Please check your connection.")
}

/**
 * Preview: Gallery grid with sample data
 */
@Preview(showBackground = true)
@Composable
fun PreviewGalleryGrid() {
    GalleryGrid(
        images = sampleImages,
        onImageClick = {},
        isLoadingNextPage = false,
        loadMore = {},
        sortOption = SortOption.NONE
    )
}

/**
 * Preview: Gallery grid showing the pagination loader at the bottom
 */
@Preview(showBackground = true)
@Composable
fun PreviewGalleryGridWithPagination() {
    GalleryGrid(
        images = sampleImages,
        onImageClick = {},
        isLoadingNextPage = true, // Set to true to show the footer
        loadMore = {},
        sortOption = SortOption.NONE
    )
}

// ==================== SCREEN LEVEL PREVIEWS ====================

/**
 * Preview: Gallery Screen - Loading State
 */
@Preview(showBackground = true)
@Composable
fun PreviewGalleryScreenLoading() {
    GalleryScreenContent(
        isLoading = true,
        error = null,
        images = emptyList(),
        onImageClick = {},
        isLoadingNextPage = false,
        loadMore = {},
        sortOption = SortOption.NONE
    )
}

/**
 * Preview: Gallery Screen - Error State
 */
@Preview(showBackground = true)
@Composable
fun PreviewGalleryScreenError() {
    GalleryScreenContent(
        isLoading = false,
        error = "Network error: Unable to fetch images",
        images = emptyList(),
        onImageClick = {},
        isLoadingNextPage = false,
        loadMore = {},
        sortOption = SortOption.NONE
    )
}

/**
 * Preview: Gallery Screen - Success State
 */
@Preview(showBackground = true)
@Composable
fun PreviewGalleryScreenSuccess() {
    GalleryScreenContent(
        isLoading = false,
        error = null,
        images = sampleImages,
        onImageClick = {},
        isLoadingNextPage = false,
        loadMore = {},
        sortOption = SortOption.NONE
    )
}

/**
 * Preview: Gallery Screen - Empty State
 */
@Preview(showBackground = true)
@Composable
fun PreviewGalleryScreenEmpty() {
    GalleryScreenContent(
        isLoading = false,
        error = null,
        images = emptyList(),
        onImageClick = {},
        isLoadingNextPage = false,
        loadMore = {},
        sortOption = SortOption.NONE
    )
}
