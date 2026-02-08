package com.pradeep.storelabassignment.presentation.detail

import androidx.lifecycle.ViewModel
import com.pradeep.storelabassignment.domain.model.PicsumImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Data class representing the state of the detail screen.
 */
data class DetailState(
    val image: PicsumImage? = null
)

/**
 * ViewModel for the detail screen.
 *
 * @param image The image to be displayed in the detail screen.
 */
class DetailViewModel(image: PicsumImage) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.value = DetailState(image = image)
    }
}