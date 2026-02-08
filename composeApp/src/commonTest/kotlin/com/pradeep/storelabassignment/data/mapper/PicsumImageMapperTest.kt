package com.pradeep.storelabassignment.data.mapper

import com.pradeep.storelabassignment.data.dto.PicsumImageDto
import com.pradeep.storelabassignment.domain.model.PicsumImage
import kotlin.test.Test
import kotlin.test.assertEquals

class PicsumImageMapperTest {

    @Test
    fun `toDomain maps correctly`() {
        // Given
        val dto = PicsumImageDto(
            id = "1",
            author = "Author",
            width = 100,
            height = 200,
            url = "url",
            downloadUrl = "download_url"
        )

        // When
        val image = dto.toDomain()

        // Then
        assertEquals("1", image.id)
        assertEquals("Author", image.author)
        assertEquals(100, image.width)
        assertEquals(200, image.height)
        assertEquals("url", image.url)
        assertEquals("download_url", image.downloadUrl)
    }
}
