package com.pradeep.storelabassignment.presentation.gallery

import com.pradeep.storelabassignment.domain.model.PicsumImage

/**
 * Represents the sorting options for the Gallery Screen.
 */
enum class SortOption {
    NONE, // Represents the default sort by ID
    AUTHOR,
    SIZE
}

/**
 * Represents the state of the Gallery Screen.
 */
data class GalleryUiState(
    val isLoading: Boolean = false,
    val isLoadingNextPage: Boolean = false,
    val isLastPage: Boolean = false,
    val images: List<PicsumImage> = emptyList(),
    val error: String? = null,
    val sortOption: SortOption = SortOption.NONE,
    val originalImages: List<PicsumImage> = emptyList()
)
