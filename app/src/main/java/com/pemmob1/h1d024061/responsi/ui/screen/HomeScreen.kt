package com.pemmob1.h1d024061.responsi.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pemmob1.h1d024061.responsi.data.model.Digimon
import com.pemmob1.h1d024061.responsi.ui.component.DigimonCard
import com.pemmob1.h1d024061.responsi.ui.component.EmptyView
import com.pemmob1.h1d024061.responsi.ui.component.ErrorView
import com.pemmob1.h1d024061.responsi.ui.component.LoadingView
import com.pemmob1.h1d024061.responsi.ui.state.UiState
import com.pemmob1.h1d024061.responsi.ui.theme.DigimonTheme
import com.pemmob1.h1d024061.responsi.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onDigimonClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var query by rememberSaveable { mutableStateOf("") }

    HomeContent(
        uiState = uiState,
        query = query,
        onQueryChange = { query = it },
        onRetry = viewModel::loadDigimon,
        onDigimonClick = onDigimonClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    uiState: UiState<List<Digimon>>,
    query: String,
    onQueryChange: (String) -> Unit,
    onRetry: () -> Unit,
    onDigimonClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Eksplorasi Digimon") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
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

            is UiState.Success -> DigimonGrid(
                digimonList = uiState.data,
                query = query,
                onQueryChange = onQueryChange,
                onDigimonClick = onDigimonClick,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Composable
private fun DigimonGrid(
    digimonList: List<Digimon>,
    query: String,
    onQueryChange: (String) -> Unit,
    onDigimonClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val filteredList = remember(digimonList, query) {
        digimonList.filter { it.name.contains(query.trim(), ignoreCase = true) }
    }

    Column(modifier = modifier.fillMaxSize()) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 12.dp),
            placeholder = { Text("Cari nama Digimon") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            singleLine = true,
            shape = MaterialTheme.shapes.extraLarge,
        )

        Text(
            text = "${filteredList.size} Digimon",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp),
        )

        if (filteredList.isEmpty()) {
            EmptyView(
                message = "Digimon \"$query\" tidak ditemukan.",
                modifier = Modifier.fillMaxWidth(),
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = filteredList,
                    key = { digimon -> digimon.id },
                ) { digimon ->
                    DigimonCard(
                        digimon = digimon,
                        onClick = { onDigimonClick(digimon.id) },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeContentPreview() {
    DigimonTheme {
        HomeContent(
            uiState = UiState.Success(
                listOf(
                    Digimon(id = 1, name = "Agumon", image = null),
                    Digimon(id = 2, name = "Gabumon", image = null),
                    Digimon(id = 3, name = "Piyomon", image = null),
                ),
            ),
            query = "",
            onQueryChange = {},
            onRetry = {},
            onDigimonClick = {},
        )
    }
}
