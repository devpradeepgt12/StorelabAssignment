package com.pradeep.storelabassignment.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data transfer object for Picsum image,
 * I have also implemented the mapper extension functions to map it with corresponding domain model
 */
@Serializable
data class PicsumImageDto(
    @SerialName("id")
    val id: String,
    @SerialName("author")
    val author: String,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("url")
    val url: String,
    @SerialName("download_url")
    val downloadUrl: String
)
