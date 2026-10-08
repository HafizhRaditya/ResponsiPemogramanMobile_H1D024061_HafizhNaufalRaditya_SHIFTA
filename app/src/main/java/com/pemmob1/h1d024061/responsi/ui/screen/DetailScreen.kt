package com.pemmob1.h1d024061.responsi.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.pemmob1.h1d024061.responsi.data.model.DigimonAttribute
import com.pemmob1.h1d024061.responsi.data.model.DigimonDescription
import com.pemmob1.h1d024061.responsi.data.model.DigimonDetail
import com.pemmob1.h1d024061.responsi.data.model.DigimonLevel
import com.pemmob1.h1d024061.responsi.data.model.DigimonType
import com.pemmob1.h1d024061.responsi.ui.component.ErrorView
import com.pemmob1.h1d024061.responsi.ui.component.LoadingView
import com.pemmob1.h1d024061.responsi.ui.state.UiState
import com.pemmob1.h1d024061.responsi.ui.theme.DigimonTheme
import com.pemmob1.h1d024061.responsi.ui.viewmodel.DetailViewModel

@Composable
fun DetailScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetailContent(
        uiState = uiState,
        onBack = onBack,
        onRetry = viewModel::loadDetail,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    uiState: UiState<DigimonDetail>,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val title = if (uiState is UiState.Success) uiState.data.name else "Detail Digimon"

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
        },
    ) { innerPadding ->
        when (uiState) {
            UiState.Loading -> LoadingView(
                modifier = Modifier.padding(innerPadding),
            )

            is UiState.Error -> ErrorView(
                message = uiState.message,
                onRetry = onRetry,
                modifier = Modifier.padding(innerPadding),
            )

            is UiState.Success -> DigimonDetailBody(
                digimon = uiState.data,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Composable
private fun DigimonDetailBody(
    digimon: DigimonDetail,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Card {
            AsyncImage(
                model = digimon.imageUrl,
                contentDescription = "Gambar ${digimon.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color.White),
                contentScale = ContentScale.Fit,
            )
        }

        Text(
            text = digimon.name,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            ),
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                InfoRow(label = "Level", value = digimon.levelText)
                HorizontalDivider()
                InfoRow(label = "Attribute", value = digimon.attributeText)
                HorizontalDivider()
                InfoRow(label = "Type", value = digimon.typeText)
                HorizontalDivider()
                InfoRow(label = "Field", value = digimon.fieldText)
                HorizontalDivider()
                InfoRow(label = "Tahun rilis", value = digimon.releaseDate ?: "Tidak diketahui")
                HorizontalDivider()
                InfoRow(
                    label = "X-Antibody",
                    value = if (digimon.xAntibody == true) "Ya" else "Tidak",
                )
            }
        }

        Text(
            text = "Deskripsi",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = digimon.description ?: "Deskripsi belum tersedia.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(96.dp),
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailContentPreview() {
    DigimonTheme {
        DetailContent(
            uiState = UiState.Success(
                DigimonDetail(
                    id = 1,
                    name = "Agumon",
                    xAntibody = false,
                    releaseDate = "1997",
                    images = null,
                    levels = listOf(DigimonLevel("Child")),
                    types = listOf(DigimonType("Reptile")),
                    attributes = listOf(DigimonAttribute("Vaccine")),
                    fields = null,
                    descriptions = listOf(
                        DigimonDescription(
                            language = "en_us",
                            description = "A Reptile Digimon which has grown and become able to walk on two legs.",
                        ),
                    ),
                ),
            ),
            onBack = {},
            onRetry = {},
        )
    }
}
