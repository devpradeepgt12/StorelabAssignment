package com.pradeep.storelabassignment.presentation.gallery

import com.pradeep.storelabassignment.domain.model.PicsumImage
import com.pradeep.storelabassignment.domain.repository.GalleryRepository
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
class GalleryViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: GalleryViewModel
    private lateinit var repository: MockGalleryRepository

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = MockGalleryRepository()
        viewModel = GalleryViewModel(repository)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is loading`() = runTest {
        assertEquals(GalleryUiState(isLoading = true), viewModel.uiState.value)
    }

    @Test
    fun `uiState reflects successful image fetch`() = runTest {
        val finalState = viewModel.uiState.first { !it.isLoading }

        assertEquals(false, finalState.isLoading)
        assertEquals(2, finalState.images.size)
        assertEquals(null, finalState.error)
    }

    @Test
    fun `uiState reflects image fetch error`() = runTest {
        repository.shouldThrowError = true
        // Recreate ViewModel to trigger new flow
        viewModel = GalleryViewModel(repository)

        val finalState = viewModel.uiState.first { !it.isLoading }

        assertEquals(false, finalState.isLoading)
        assertEquals("Error fetching images", finalState.error)
        assertEquals(true, finalState.images.isEmpty())
    }

    @Test
    fun `toggleSort sorts by author`() = runTest {
        // Wait for initial load
        viewModel.uiState.first { !it.isLoading }

        viewModel.toggleSort(SortOption.AUTHOR)

        val sortedState = viewModel.uiState.first { it.sortOption == SortOption.AUTHOR }

        assertEquals(SortOption.AUTHOR, sortedState.sortOption)
        assertEquals("Author A", sortedState.images.first().author)
    }

    @Test
    fun `toggleSort sorts by size`() = runTest {
        // Wait for initial load
        viewModel.uiState.first { !it.isLoading }

        viewModel.toggleSort(SortOption.SIZE)

        val sortedState = viewModel.uiState.first { it.sortOption == SortOption.SIZE }

        assertEquals(SortOption.SIZE, sortedState.sortOption)
        assertEquals("2", sortedState.images.first().id)
    }

    @Test
    fun `toggleSort reverts to original order`() = runTest {
        // Wait for initial load
        viewModel.uiState.first { !it.isLoading }

        viewModel.toggleSort(SortOption.AUTHOR)
        // Wait for sort to apply
        viewModel.uiState.first { it.sortOption == SortOption.AUTHOR }

        viewModel.toggleSort(SortOption.AUTHOR)
        // Toggle back
        val finalState = viewModel.uiState.first { it.sortOption == SortOption.NONE }

        assertEquals(SortOption.NONE, finalState.sortOption)
        assertEquals("1", finalState.images.first().id)
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
