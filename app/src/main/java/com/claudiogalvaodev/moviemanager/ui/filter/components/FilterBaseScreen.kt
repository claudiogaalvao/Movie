package com.claudiogalvaodev.moviemanager.ui.filter.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.ui.menu.schedulednotifications.ui.theme.LightPurple

@Composable
fun FilterBaseScreen(
    onApplyFilter: () -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LightPurple
                ),
                onClick = { onApplyFilter() }
            ) {
                Text(
                    text = stringResource(id = R.string.filter_button_apply).uppercase(),
                    color = Color.White
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun FilterBaseScreenPreview() {
    FilterBaseScreen(
        onApplyFilter = {}
    ) {
        Text(text = "Content")
    }
}
