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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.claudiogalvaodev.moviemanager.ui.filter.FiltersViewModel
import com.claudiogalvaodev.moviemanager.ui.filter.SimpleFilterItem
import com.claudiogalvaodev.moviemanager.ui.menu.schedulednotifications.ui.theme.LightPurple
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun FilterProviderScreen(
    viewModel: FiltersViewModel = koinViewModel()
) {
    val providers by viewModel.providers.collectAsState()
    FilterList(
        providers = providers
    )
}

@Composable
private fun FilterList(
    providers: List<SimpleFilterItem> = emptyList()
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(providers) { provider ->
            CheckboxItem(provider, {})
        }
    }
}

@Composable
fun CheckboxItem(
    item: SimpleFilterItem,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .border(1.dp, if (item.isSelected) LightPurple else Color.Transparent, RoundedCornerShape(8.dp))
            .padding(start = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.name,
            color = Color.White
        )
        Checkbox(
            checked = item.isSelected,
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
        item = SimpleFilterItem("Netflix", true),
        onCheckedChange = {}
    )
}

@Composable
@Preview(backgroundColor = 0xFF070D2D, showBackground = true)
private fun FilterListPreview() {
    FilterList(
        providers = listOf(
            SimpleFilterItem("Netflix", true),
            SimpleFilterItem("Amazon Prime", false),
            SimpleFilterItem("HBO", true),
            SimpleFilterItem("Disney+", false),
            SimpleFilterItem("Apple TV", true),
            SimpleFilterItem("Paramount+", false),
            SimpleFilterItem("Globoplay", true)
        )
    )
}