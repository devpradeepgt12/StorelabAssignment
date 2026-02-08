package com.pradeep.storelabassignment.domain.model

/**
 * Domain Entity - Business Logic Model
 * - No serialization annotation (pure business object)
 * - Represents business logic, independent of data source
 * - If API changes, only data layer mappers change, not this
 */
data class PicsumImage(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val downloadUrl: String
)
