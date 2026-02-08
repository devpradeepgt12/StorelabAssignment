package com.pradeep.storelabassignment.presentation.gallery.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.pradeep.storelabassignment.domain.model.PicsumImage
import com.pradeep.storelabassignment.theme.NUM_1F
import com.pradeep.storelabassignment.theme.NUM_4
import org.jetbrains.compose.resources.stringResource
import storelabassignment.composeapp.generated.resources.Res
import storelabassignment.composeapp.generated.resources.image_description

/**
 * Atomic component: Single gallery item card
 * - Pure presentational component with no side effects
 * - Easy to test and reuse
 */
@Composable
fun GalleryItemCard(
    image: PicsumImage,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(NUM_4)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = image.downloadUrl,
            contentDescription = stringResource(Res.string.image_description, image.author),
            modifier = Modifier.aspectRatio(NUM_1F),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
    }
}
