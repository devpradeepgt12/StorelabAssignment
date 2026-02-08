package com.pradeep.storelabassignment.presentation.gallery

import com.pradeep.storelabassignment.domain.model.PicsumImage

/**
 * Data class representing the state of the gallery screen.
 * The single source of truth for the UI
 */
data class GalleryUiState(
    val isLoading: Boolean = false,
    val images: List<PicsumImage> = emptyList(),
    val originalImages: List<PicsumImage> = emptyList(), // Add this
    val error: String? = null,
    val selectedImage: PicsumImage? = null,
    val sortOption: SortOption = SortOption.NONE
)

/**
 * Enum representing the sorting options available for the gallery.
 */
enum class SortOption { NONE, AUTHOR, SIZE }
