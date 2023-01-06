package com.claudiogalvaodev.moviemanager.ui.menu.schedulednotifications

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.ui.model.ScheduledNotificationsModel

class ScheduledNotificationsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val allItems = listOf<ScheduledNotificationsModel>()
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.background
            ) {
                AllScheduledNotification(allItems)
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
//            CoilImage(request = ImageRequest
//                .Builder(LocalContext.current)
//                .data(item.moviePosterPath)
//                .build()
//            ) {
//
//            }
            Image(
                modifier = Modifier
                    .width(80.dp)
                    .height(120.dp),
                painter = painterResource(id = R.drawable.poster_example),
                contentDescription = item.movieName,
                contentScale = ContentScale.Crop
            )
            Text(
                text = item.movieName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 12.dp)
            )
            Text(
                text = item.remindAt.toString(),
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 12.dp),
                textAlign = TextAlign.End
            )
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