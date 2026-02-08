package com.pradeep.storelabassignment.data.remote

import com.pradeep.storelabassignment.data.dto.PicsumImageDto

/**
 * Defines the remote API for fetching images from Picsum.
 * This interface abstracts the data source, allowing for mock implementations in tests.
 */
interface PicsumApi {
    suspend fun getImages(page: Int = 1, limit: Int = 30): List<PicsumImageDto>
}
