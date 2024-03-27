package com.bultrain.vlak_app_test.ui.guide

import androidx.activity.addCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bultrain.vlak_app_test.R

@Composable
fun MakeGuideMoreInfoScreen(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    viewModel: GuideViewModel
) {
    val data = viewModel.getOnMoreInfoTopic()

    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    DisposableEffect(backDispatcher) {
        val callback = backDispatcher?.addCallback(onBackPressed = { onClose() })
        onDispose {
            callback?.remove()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Icon(
            painter = painterResource(id = R.drawable.close),
            contentDescription = "Close",
            modifier = Modifier
                .size(30.dp)
                .padding(top = 16.dp, start = 16.dp)
                .clickable { onClose() },
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url + "/guide/images/" + data.image)
                .build(),
            contentDescription = "topic image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(200.dp)
                .padding(top = 16.dp, bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(20.dp))
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            Text(
                text = data.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = data.subtitle,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            data.content.forEach {
                Text(
                    text = it.text,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                if (it.image.isNotEmpty()) {
                    // Load image from server with Coil
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(url + "/guide/images/" + it.image)
                            .build(),
                        contentDescription = "topic image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .clip(RoundedCornerShape(20.dp))
                    )
                }
            }
        }
    }
}