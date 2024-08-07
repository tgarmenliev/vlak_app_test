package com.bultrain.vlak_app_test.ui.guide

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bultrain.vlak_app_test.models.guide.Guide
import com.bultrain.vlak_app_test.network.BASE_URL
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

const val url = BASE_URL

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselScreen(
    modifier: Modifier = Modifier,
    data: List<Guide.AllTopics>,
    onCardClick: (Int) -> Unit = { },
    viewModel: GuideViewModel
) {

    // Define the delay between auto-scrolling to the next page
    val autoScrollDuration = 3000L

    // Create a PagerState to keep track of the current page and the current page offset
    val pagerState = rememberPagerState(
        initialPage = viewModel.getCurrentTopic(),
        initialPageOffsetFraction = 0f,
        pageCount = { data.size }
    )

    // Observe the isDragged state and start the auto-scrolling when the user stops dragging
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    if (isDragged.not()) {
        with(pagerState) {
            var currentPageKey by remember { mutableIntStateOf(0) }
            LaunchedEffect(key1 = currentPageKey) {
                launch {
                    delay(timeMillis = autoScrollDuration)
                    val nextPage = (currentPage + 1).mod(pageCount)
                    animateScrollToPage(page = nextPage)
                    currentPageKey = nextPage
                }
            }
        }
    }

    // Create a Modifier that applies a carousel transition based on the current page and the current page offset
    fun Modifier.carouselTransition(page: Int, pagerState: PagerState) =
        graphicsLayer {
            val pageOffset =
                ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

            val transformation =
                lerp(
                    start = 0.7f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
            alpha = transformation
            scaleY = transformation
        }

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth(),
            pageSpacing = 16.dp,
            contentPadding = PaddingValues(32.dp)
        ) {
            index ->
            CarouselItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .carouselTransition(page = index, pagerState = pagerState),
                data = data[index],
                onClick = { onCardClick(index) }
            )
        }
    }
}

@Composable
fun CarouselItem(
    modifier: Modifier = Modifier,
    data: Guide.AllTopics,
    onClick: () -> Unit = { }
) {

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Box(modifier = Modifier
            .fillMaxHeight()
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url + "/guide/images/" + data.image)
                    .build(),
                contentDescription = "topic image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 300f
                    )
                )
            )

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = data.subtitle,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}