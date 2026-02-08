package com.pradeep.storelabassignment.presentation.image_detail

import com.pradeep.storelabassignment.domain.model.PicsumImage
import com.pradeep.storelabassignment.domain.repository.GalleryRepository
import com.pradeep.storelabassignment.presentation.gallery.GalleryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ImageDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: GalleryViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = GalleryViewModel(MockGalleryRepository())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `selectImage updates the selectedImage state`() = runTest {
        // Given
        val image = PicsumImage("1", "Author", 100, 100, "", "")
        viewModel.uiState.first { !it.isLoading } // Wait for initial load

        // When
        viewModel.selectImage(image)

        // Then
        val updatedState = viewModel.uiState.first { it.selectedImage == image }
        assertEquals(image, updatedState.selectedImage)
    }

    @Test
    fun `deselectImage updates the selectedImage state to null`() = runTest {
        // Given
        val image = PicsumImage("1", "Author", 100, 100, "", "")
        viewModel.uiState.first { !it.isLoading } // Wait for initial load
        viewModel.selectImage(image)
        viewModel.uiState.first { it.selectedImage == image } // Wait for selection

        // When
        viewModel.selectImage(null)

        // Then
        val updatedState = viewModel.uiState.first { it.selectedImage == null }
        assertEquals(null, updatedState.selectedImage)
    }
}

class MockGalleryRepository : GalleryRepository {
    var shouldThrowError = false
    private val images = listOf(
        PicsumImage("1", "Author B", 100, 200, "", ""),
        PicsumImage("2", "Author A", 50, 50, "", "")
    )

    override suspend fun fetchImages(page: Int, limit: Int): Result<List<PicsumImage>> {
        return if (shouldThrowError) {
            Result.failure(Exception("Error fetching images"))
        } else {
            Result.success(images)
        }
    }
}
