package com.pradeep.storelabassignment.data.remote

import com.pradeep.storelabassignment.BuildConfig
import com.pradeep.storelabassignment.data.dto.PicsumImageDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

/**
 * The concrete implementation of the Picsum API that uses Ktor for networking.
 * It fetches images from the Picsum API.
 * BASE_URL is coming from the BuildConfig using gradle-buildconfig plugin
 * @param client The Ktor HTTP client used for making network requests using Ktor's automatic engine resolution
 * @see PicsumApi
 */
class PicsumApiImpl(private val client: HttpClient) : PicsumApi {

    override suspend fun getImages(page: Int, limit: Int): List<PicsumImageDto> {
        return client.get("${BuildConfig.BASE_URL}?page=$page&limit=$limit").body()
    }
}
