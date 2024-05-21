package com.claudiogalvaodev.moviemanager.ui.filter.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.claudiogalvaodev.moviemanager.ui.filter.FiltersViewModel
import com.claudiogalvaodev.moviemanager.ui.filter.components.CheckboxItem
import com.claudiogalvaodev.moviemanager.ui.filter.components.FilterBaseScreen
import com.claudiogalvaodev.moviemanager.ui.model.ProviderModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun FilterProviderScreen(
    viewModel: FiltersViewModel = koinViewModel()
) {
    val providers by viewModel.providers.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getProviders()
    }

    FilterBaseScreen(
        onApplyFilter = {
            // TODO: Implement apply filter
        }
    ) {
        FilterList(
            providers = providers,
            onSelectProvider = { providerId ->
                viewModel.selectProvider(providerId)
            }
        )
    }
}

@Composable
private fun FilterList(
    providers: List<ProviderModel> = emptyList(),
    onSelectProvider: (providerId: Int) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(providers) { provider ->
            CheckboxItem(
                text = provider.name,
                checked = provider.isSelected,
                onCheckedChange = {
                    onSelectProvider(provider.id)
                }
            )
        }
    }
}

@Composable
@Preview(backgroundColor = 0xFF070D2D, showBackground = true)
private fun FilterListPreview() {
    FilterList(
        providers = listOf(
            ProviderModel(1, "Netflix", "", true),
            ProviderModel(2, "Amazon Prime", "", false),
            ProviderModel(3, "HBO", "", true),
            ProviderModel(4, "Disney+", "", false),
            ProviderModel(5, "Apple TV", "", true),
            ProviderModel(6, "Paramount+", "", false),
            ProviderModel(7, "Globoplay", "", true)
        ),
        onSelectProvider = {}
    )
}