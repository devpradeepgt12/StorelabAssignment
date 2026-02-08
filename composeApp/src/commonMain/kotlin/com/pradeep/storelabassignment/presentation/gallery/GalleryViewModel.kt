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

/**
 * ViewModel for the gallery screen.
 *
 * @param repository The repository to fetch images from.
 */
class GalleryViewModel(repository: GalleryRepository) : ViewModel() {

    private val _sortOption = MutableStateFlow(SortOption.NONE)
    private val _selectedImage = MutableStateFlow<PicsumImage?>(null)

    private val _imagesResultFlow = flow {
        emit(repository.fetchImages())
    }

    val uiState: StateFlow<GalleryUiState> = combine(
        _imagesResultFlow,
        _sortOption,
        _selectedImage
    ) { result, sortOption, selectedImage ->
        result.fold(
            onSuccess = { images ->
                val sortedImages = when (sortOption) {
                    SortOption.AUTHOR -> images.sortedBy { it.author }
                    SortOption.SIZE -> images.sortedBy { it.width * it.height }
                    SortOption.NONE -> images
                }
                GalleryUiState(
                    isLoading = false,
                    images = sortedImages,
                    originalImages = images,
                    sortOption = sortOption,
                    selectedImage = selectedImage
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
