package com.pradeep.storelabassignment.presentation.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pradeep.storelabassignment.domain.model.PicsumImage
import com.pradeep.storelabassignment.domain.repository.GalleryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the gallery screen, using a reactive approach with `stateIn`.
 */
class GalleryViewModel(private val repository: GalleryRepository) : ViewModel() {

    private val _sortOption = MutableStateFlow(SortOption.NONE)
    private val _selectedImage = MutableStateFlow<PicsumImage?>(null)

    // --- Pagination State Holder ---
    private data class PagedData(
        val images: List<PicsumImage> = emptyList(),
        val isLoading: Boolean = false,
        val isLastPage: Boolean = false
    )
    private val _paginationState = MutableStateFlow(PagedData())
    private var currentPage = 2 // Page 1 is handled by the initial flow

    // This flow handles the initial data load (Page 1) and only ever runs once.
    private val _initialImagesResultFlow = flow {
        emit(repository.fetchImages(page = 1))
    }

    // The UI state is a combination of the initial load, paginated data, and user actions.
    val uiState: StateFlow<GalleryUiState> = combine(
        _initialImagesResultFlow,
        _paginationState,
        _sortOption,
        _selectedImage
    ) { initialResult, pagedData, sortOption, selectedImage ->
        initialResult.fold(
            onSuccess = { initialImages ->
                val allImages = initialImages + pagedData.images
                val sortedImages = when (sortOption) {
                    SortOption.AUTHOR -> allImages.sortedBy { it.author }
                    SortOption.SIZE -> allImages.sortedBy { it.width * it.height }
                    SortOption.NONE -> allImages.sortedBy { it.id.toIntOrNull() ?: 0 }
                }
                GalleryUiState(
                    isLoading = false,
                    images = sortedImages,
                    originalImages = allImages,
                    sortOption = sortOption,
                    selectedImage = selectedImage,
                    isLoadingNextPage = pagedData.isLoading,
                    isLastPage = pagedData.isLastPage
                )
            },
            onFailure = {
                GalleryUiState(isLoading = false, error = it.message)
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = GalleryUiState(isLoading = true)
    )

    /**
     * Fetches the next page of images. This is called from the UI.
     */
    fun loadMoreImages() {
        if (_paginationState.value.isLoading || _paginationState.value.isLastPage) return

        viewModelScope.launch {
            _paginationState.update { it.copy(isLoading = true) }
            repository.fetchImages(page = currentPage)
                .onSuccess { newImages ->
                    _paginationState.update {
                        it.copy(
                            isLoading = false,
                            images = it.images + newImages,
                            isLastPage = newImages.isEmpty()
                        )
                    }
                    if (newImages.isNotEmpty()) currentPage++
                }
                .onFailure {
                    // On failure, stop pagination.
                    _paginationState.update { it.copy(isLoading = false, isLastPage = true) }
                }
        }
    }

    fun selectImage(image: PicsumImage?) {
        _selectedImage.value = image
    }

    fun toggleSort(option: SortOption) {
        if (_sortOption.value == option) {
            _sortOption.value = SortOption.NONE
        } else {
            _sortOption.value = option
        }
    }
}
