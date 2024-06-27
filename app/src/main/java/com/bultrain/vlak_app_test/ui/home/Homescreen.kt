package com.bultrain.vlak_app_test.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.bultrain.vlak_app_test.R
import com.bultrain.vlak_app_test.room.TripHeading
import com.bultrain.vlak_app_test.ui.composables.MakeButton
import com.bultrain.vlak_app_test.ui.composables.MakeImageHeader
import com.bultrain.vlak_app_test.ui.error.ErrorScreen
import com.bultrain.vlak_app_test.ui.loading.LoadingScreen
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun Homescreen(
    modifier: Modifier = Modifier,
    viewModel: HomescreenViewModel,
    onSettingsClick: () -> Unit = { },
    onNumberClick: (String) -> Unit = { },
    onClick: () -> Unit = { },
    onExploreClick: () -> Unit = { }
) {
    when (val homeState = viewModel.homeState) {
        is HomeState.Success -> {
            MakeHomescreen(
                modifier = modifier,
                data = homeState.numbers,
                viewModel = viewModel,
                onSettingsClick = onSettingsClick,
                onRefreshClick = { viewModel.getRecentTrips() },
                onNumberClick = onNumberClick,
                onClick = onClick,
                onExploreClick = onExploreClick
            )
        }
        is HomeState.Loading -> {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        }
        is HomeState.Error -> {
            ErrorScreen(error = homeState.error)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MakeHomescreen(
    modifier: Modifier = Modifier,
    data: List<String>,
    viewModel: HomescreenViewModel,
    onSettingsClick: () -> Unit = { },
    onRefreshClick: () -> Unit = { },
    onNumberClick: (String) -> Unit = { },
    onClick: () -> Unit = { },
    onExploreClick: () -> Unit = { },
) {

//    val pagerState = rememberPagerState(pageCount = {
//        data.size
//    })

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        MakeImageHeader(
            text = R.string.home,
            image = painterResource(id = R.drawable.home_back2),
            modifier = Modifier.height(200.dp),
            hasButton = true,
            buttonIcon = painterResource(id = R.drawable.settings),
            onButtonClicked = onSettingsClick
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Box(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
            ) {
                MakeRecentTrips(
                    modifier = Modifier
                        .fillMaxWidth(),
                    recentTrips = viewModel.recentTrips.value,
                    onClick = onClick,
                    onRefreshClick = onRefreshClick
                )
            }

            HorizontalPagerWithArrows(data = data, onNumberClick = onNumberClick)

//            fun Modifier.carouselTransition(page: Int, pagerState: PagerState) =
//                graphicsLayer {
//                    val pageOffset =
//                        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
//
//                    val transformation =
//                        lerp(
//                            start = 0.7f,
//                            stop = 1f,
//                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
//                        )
//                    alpha = transformation
//                    scaleY = transformation
//                }
//
//            if (data.isNotEmpty()) {
//                Text(
//                    text = stringResource(id = R.string.saved_train_routes),
//                    style = MaterialTheme.typography.bodyMedium,
//                    fontWeight = FontWeight.SemiBold,
//                    modifier = Modifier
//                        .padding(horizontal = 24.dp)
//                )
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                ) {
//                    HorizontalPager(
//                        state = pagerState,
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        pageSpacing = 16.dp,
//                        contentPadding = PaddingValues(
//                            top = 16.dp,
//                            start = 24.dp,
//                            end = 24.dp,
//                            bottom = 8.dp
//                        )
//                    ) { index ->
//                        NumberItem(
//                            number = data[index],
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .carouselTransition(page = index, pagerState = pagerState),
//                            onClick = onNumberClick
//                        )
//                    }
//                }
//
//            }

            Spacer(modifier = Modifier.height(16.dp))

            MakeImageHeader(
                text = R.string.explore,
                image = painterResource(id = R.drawable.explore_pic),
                modifier = Modifier
                    .height(160.dp)
                    .padding(bottom = 22.dp, start = 16.dp, end = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(2.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(16.dp))
                    .shadow(10.dp, RoundedCornerShape(16.dp))
                    .clickable(onClick = onExploreClick),
                hasButton = false,
                leftAlignText = true
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerWithArrows(data: List<String>, onNumberClick: (String) -> Unit) {
    val pagerState = rememberPagerState(pageCount = {
        data.size
    })
    val coroutineScope = rememberCoroutineScope()

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

    if (data.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.saved_train_routes),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (pagerState.currentPage > 0) {
                    // Left arrow
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    (pagerState.currentPage - 1).coerceAtLeast(
                                        0
                                    )
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(start = 8.dp, top = 16.dp, bottom = 8.dp)
                            .weight(0.2f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Previous"
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.weight(0.2f))
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    pageSpacing = 16.dp,
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    )
                ) { index ->
                    NumberItem(
                        number = data[index],
                        modifier = Modifier
                            .carouselTransition(page = index, pagerState = pagerState),
                        onClick = onNumberClick
                    )
                }

                if (pagerState.currentPage < data.size - 1) {
                    // Right arrow
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    (pagerState.currentPage + 1).coerceAtMost(
                                        data.size - 1
                                    )
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(top = 16.dp, end = 8.dp, bottom = 8.dp)
                            .weight(0.2f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Next"
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.weight(0.2f))
                }
            }
        }
    }
}

@Composable
fun NumberItem(
    number: String,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick(number) }),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.secondaryContainer,
                    RoundedCornerShape(16.dp)
                )
                .padding(8.dp)
        ) {
            Text(
                text = number,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun MakeRecentTrips(
    modifier: Modifier,
    recentTrips: List<TripHeading>,
    onClick: () -> Unit = { },
    onRefreshClick: () -> Unit = { }
) {
    if (recentTrips.isNotEmpty()) {
        Column(
            modifier = modifier
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.recent_trips),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(8.dp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.refresh),
                    contentDescription = "refresh",
                    modifier = Modifier
                        .size(28.dp)
                        .padding(top = 4.dp)
                        .clickable {
                            onRefreshClick()
                        }
                )
            }

            recentTrips.forEach {
                MakeRecentTrip(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    trip = it
                )
            }

            MakeButton(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = R.string.view_all_trips,
                onClick = onClick
            )
        }
    } else {
        Text(
            text = stringResource(id = R.string.no_added_trips),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MakeRecentTrip(modifier: Modifier, trip: TripHeading) {
    Column(
        modifier = modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(16.dp)
            )
            .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(16.dp))
            .padding(8.dp)
    ) {
        Text(
            text = trip.departureDate,
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(start = 4.dp, end = 4.dp)
        )
        Text(
            text = trip.route,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
        )
        Row {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = trip.departureTime,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.right),
                    contentDescription = "time arrow",
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = trip.arrivalTime,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.duration),
                    contentDescription = "duration",
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "${trip.duration} ${stringResource(id = R.string.hours_short)}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp),
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}
