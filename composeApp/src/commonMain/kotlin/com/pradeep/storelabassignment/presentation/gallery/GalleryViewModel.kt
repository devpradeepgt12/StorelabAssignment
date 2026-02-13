package com.pradeep.storelabassignment.presentation.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pradeep.storelabassignment.domain.model.PicsumImage
import com.pradeep.storelabassignment.domain.repository.GalleryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GalleryViewModel(private val repository: GalleryRepository) : ViewModel() {

    private val _sortOption = MutableStateFlow(SortOption.NONE)

    // This state is specifically for passing data to the detail screen.
    private val _detailImage = MutableStateFlow<PicsumImage?>(null)
    val detailImage = _detailImage.asStateFlow()

    private data class PagedData(
        val images: List<PicsumImage> = emptyList(),
        val isLoading: Boolean = false,
        val isLastPage: Boolean = false
    )
    private val _paginationState = MutableStateFlow(PagedData())
    private var currentPage = 2

    private val _initialImagesResultFlow = flow {
        emit(repository.fetchImages(page = 1))
    }

    val uiState: StateFlow<GalleryUiState> = combine(
        _initialImagesResultFlow, _paginationState, _sortOption
    ) { initialResult, pagedData, sortOption ->
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
                    _paginationState.update { it.copy(isLoading = false, isLastPage = true) }
                }
        }
    }

    fun onImageSelectedForDetail(image: PicsumImage) {
        _detailImage.value = image
    }

    fun onDetailScreenDismissed() {
        _detailImage.value = null
    }

    fun toggleSort(option: SortOption) {
        if (_sortOption.value == option) {
            _sortOption.value = SortOption.NONE
        } else {
            _sortOption.value = option
        }
    }
}
