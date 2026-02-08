package com.pradeep.storelabassignment.data

import com.pradeep.storelabassignment.data.dto.PicsumImageDto
import com.pradeep.storelabassignment.data.remote.PicsumApi
import com.pradeep.storelabassignment.data.repository.GalleryRepositoryImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GalleryRepositoryImplTest {

    @Test
    fun `fetchImages returns success`() = runTest {
        // Given
        val api = MockPicsumApi(shouldSucceed = true)
        val repository = GalleryRepositoryImpl(api)

        // When
        val result = repository.fetchImages()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
    }

    @Test
    fun `fetchImages returns failure`() = runTest {
        // Given
        val api = MockPicsumApi(shouldSucceed = false)
        val repository = GalleryRepositoryImpl(api)

        // When
        val result = repository.fetchImages()

        // Then
        assertTrue(result.isFailure)
    }
}

class MockPicsumApi(private val shouldSucceed: Boolean) : PicsumApi {
    override suspend fun getImages(page: Int, limit: Int): List<PicsumImageDto> {
        return if (shouldSucceed) {
            listOf(PicsumImageDto("1", "author", 100, 100, "", ""))
        } else {
            throw Exception("API Error")
        }
    }
}