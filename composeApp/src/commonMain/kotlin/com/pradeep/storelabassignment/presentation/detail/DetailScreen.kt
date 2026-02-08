package com.pradeep.storelabassignment.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil3.compose.AsyncImage
import com.pradeep.storelabassignment.domain.model.PicsumImage
import com.pradeep.storelabassignment.theme.NUM_1F
import com.pradeep.storelabassignment.theme.NUM_24
import com.pradeep.storelabassignment.theme.NUM_4
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import storelabassignment.composeapp.generated.resources.*

/**
 * Details Screen - Adhering to MVI by observing state from the ViewModel.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    // The image from navigation is now used solely to initialize the ViewModel.
    image: PicsumImage,
    onBack: () -> Unit,
    viewModel: DetailViewModel = koinViewModel<DetailViewModel>(key = image.id) { parametersOf(image) }
) {
    // The UI strictly observes state from the ViewModel, making it the single source of truth.
    val state by viewModel.uiState.collectAsState()
    val imageToShow = state.image

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.detail_title),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.back_button_description)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        // Render content only when the image data is available in the state.
        if (imageToShow != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .background(MaterialTheme.colorScheme.background)
            ) {
                // Full Screen Image Container
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(NUM_1F)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    AsyncImage(
                        model = imageToShow.downloadUrl,
                        contentDescription = stringResource(Res.string.image_description, imageToShow.author),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }

                // Metadata Card
                Column(
                    modifier = Modifier.padding(NUM_24)
                ) {
                    // Author Section
                    Text(
                        text = stringResource(Res.string.captured_by),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(NUM_4))

                    Text(
                        text = imageToShow.author,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Divider(
                        modifier = Modifier.padding(vertical = NUM_24),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    // Technical Details Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        DetailItem(
                            label = stringResource(Res.string.detail_width),
                            value = stringResource(Res.string.pixels_unit, imageToShow.width)
                        )
                        DetailItem(
                            label = stringResource(Res.string.detail_height),
                            value = stringResource(Res.string.pixels_unit, imageToShow.height)
                        )
                        DetailItem(
                            label = stringResource(Res.string.detail_id),
                            value = stringResource(Res.string.id_prefix, imageToShow.id)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.DetailItem(label: String, value: String) {
    Column(modifier = Modifier.weight(NUM_1F)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )
    }
}
