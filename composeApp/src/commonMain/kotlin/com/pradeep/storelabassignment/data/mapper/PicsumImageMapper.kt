package com.pradeep.storelabassignment.data.mapper

import com.pradeep.storelabassignment.data.dto.PicsumImageDto
import com.pradeep.storelabassignment.domain.model.PicsumImage

/**
 * Converts a single PicsumImageDto to a domain PicsumImage model.
 */
fun PicsumImageDto.toDomain(): PicsumImage {
    return PicsumImage(
        id = this.id,
        author = this.author,
        width = this.width,
        height = this.height,
        url = this.url,
        downloadUrl = this.downloadUrl
    )
}

/**
 * Converts a list of PicsumImageDto to a list of domain PicsumImage models.
 */
fun List<PicsumImageDto>.toDomain(): List<PicsumImage> {
    return this.map { it.toDomain() }
}

/**
 * Converts a domain PicsumImage model back to a data transfer object (PicsumImageDto).
 */
fun PicsumImage.toRemote(): PicsumImageDto {
    return PicsumImageDto(
        id = this.id,
        author = this.author,
        width = this.width,
        height = this.height,
        url = this.url,
        downloadUrl = this.downloadUrl
    )
}
