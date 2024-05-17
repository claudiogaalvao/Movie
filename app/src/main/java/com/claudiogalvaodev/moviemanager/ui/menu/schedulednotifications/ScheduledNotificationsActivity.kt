package com.claudiogalvaodev.moviemanager.ui.menu.schedulednotifications

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.ui.model.ScheduledNotificationsModel
import com.claudiogalvaodev.moviemanager.utils.extensions.launchWhenResumed
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScheduledNotificationsActivity : ComponentActivity() {

    private val viewModel: ScheduledNotificationsViewModel by viewModel()

    private var allItems: List<ScheduledNotificationsModel> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getScheduledNotifications()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.background
            ) {
                AllScheduledNotification(allItems)
            }
        }
    }

    private fun getScheduledNotifications() {
        launchWhenResumed {
            viewModel.allScheduledNotificationResult.collectLatest {
                val scheduledNotifications = it.getOrNull() ?: emptyList()
                allItems = scheduledNotifications
            }
        }
    }
}

@Composable
fun AllScheduledNotification(allItems: List<ScheduledNotificationsModel>) {
    Scaffold {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(allItems) { item ->
                ScheduledItemCard(item = item)
            }
        }
    }
}

@Composable
fun ScheduledItemCard(item: ScheduledNotificationsModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .background(Color.White),
        shape = RoundedCornerShape(4.dp),
        elevation = 2.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .width(80.dp)
                    .height(120.dp),
                painter = painterResource(id = R.drawable.poster_example),
                contentDescription = item.movieName,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(start = 12.dp)
            ) {
                Text(
                    text = item.movieName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                )
                Text(
                    text = "Release date: ${item.remindAt}",
                    fontSize = 16.sp
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val allItems = listOf(
        ScheduledNotificationsModel(
            id = 1,
            movieId = 1,
            movieName = "Adão Negro",
            moviePosterPath = "https://www.ultimato.com.br/image/atualiza_home/principal/ultimas/opiniao/2022/10_out/opi_1-11-22_carlos_caldas_adao_negro.jpg",
            remindAt = 1
        )
    )
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        AllScheduledNotification(allItems)
    }
}