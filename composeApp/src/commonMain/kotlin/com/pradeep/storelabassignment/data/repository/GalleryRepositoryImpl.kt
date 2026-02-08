package com.pradeep.storelabassignment.data.repository

import com.pradeep.storelabassignment.data.mapper.toDomain
import com.pradeep.storelabassignment.data.remote.PicsumApi
import com.pradeep.storelabassignment.domain.model.PicsumImage
import com.pradeep.storelabassignment.domain.repository.GalleryRepository

/**
 * Gallery Repository Implementation - Data Layer
 * - Implements domain repository interface
 * - Handles API calls and data mapping
 * - If API changes, only this layer is affected
 * - Domain and presentation layers remain unchanged
 */
class GalleryRepositoryImpl(private val api: PicsumApi) : GalleryRepository {

    /**
     * Fetch images from API and map to domain entities
     * - Calls API to get serialized data
     * - Maps serialized models to domain models using the toDomain() extension function
     * - Returns domain entities to the presentation layer
     */
    override suspend fun fetchImages(page: Int, limit: Int): Result<List<PicsumImage>> {
        return try {
            val remoteImages = api.getImages(page, limit)
            val domainImages = remoteImages.toDomain()
            Result.success(domainImages)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
