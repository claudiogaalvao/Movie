package com.claudiogalvaodev.moviemanager.ui.menu.schedulednotifications

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.claudiogalvaodev.moviemanager.ui.menu.schedulednotifications.ui.theme.CineSeteTheme

class ScheduledNotificationsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val allItems = listOf("Item 1", "Item 2", "Item 3")
            CineSeteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AllScheduledNotification(allItems)
                }
            }
        }
    }
}

@Composable
fun AllScheduledNotification(allItems: List<String>) {
    Scaffold {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(allItems) { item ->
                ScheduledItemCard(item = item)
            }
        }
    }
}

@Composable
fun ScheduledItemCard(item: String) {
    Text(text = item)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val allItems = listOf("Item 1", "Item 2", "Item 3")
    CineSeteTheme {
        AllScheduledNotification(allItems)
    }
}