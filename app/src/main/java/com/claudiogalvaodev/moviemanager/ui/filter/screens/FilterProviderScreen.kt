package com.claudiogalvaodev.moviemanager.ui.filter.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.claudiogalvaodev.moviemanager.ui.filter.FiltersViewModel
import com.claudiogalvaodev.moviemanager.ui.menu.schedulednotifications.ui.theme.LightPurple
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

    FilterList(
        providers = providers,
        onSelectProvider = { providerId ->
            viewModel.selectProvider(providerId)
        }
    )
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
fun CheckboxItem(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .border(
                1.dp,
                if (checked) LightPurple else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
            .padding(start = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            color = Color.White
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                uncheckedColor = Color.LightGray,
                checkedColor = LightPurple
            )
        )
    }
}

@Composable
@Preview(backgroundColor = 0xFF070D2D, showBackground = true)
private fun CheckboxItemPreview() {
    CheckboxItem(
        text = "Netflix",
        checked = true,
        onCheckedChange = {}
    )
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