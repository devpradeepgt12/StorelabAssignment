package com.pradeep.storelabassignment.domain.repository

import com.pradeep.storelabassignment.domain.model.PicsumImage

/**
 * Gallery Repository Interface - Domain Contract
 * - Defines the contract for gallery data operations
 * - Independent of implementation details (API, database, etc.)
 * - Business logic remains unchanged even if data source changes
 */
interface GalleryRepository {
    /**
     * Fetch list of images
     * @param page Page number for pagination
     * @param limit Number of images per page
     * @return Result containing List of domain PicsumImage entities or error
     */
    suspend fun fetchImages(page: Int = 1, limit: Int = 30): Result<List<PicsumImage>>
}
