package com.example.vlak_app_test.ui.composables

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vlak_app_test.ui.theme.BottomBarContainerColor
import com.example.vlak_app_test.ui.theme.ChosenBottomBarColor
import com.example.vlak_app_test.ui.theme.PrimaryDarkColor
import com.example.vlak_app_test.ui.theme.PrimaryLightColor

data class BottomBarItem(
    val title: Int,
    val filledIcon: Painter,
    val outlinedIcon: Painter,
    val route: String
)

@Composable
fun MakeBottomBar(
    items: List<BottomBarItem>,
    modifier: Modifier = Modifier
) {
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar(
        containerColor = BottomBarContainerColor
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                },
                label = {
                    Text(text = stringResource(id = item.title))
                },
                icon = {
                    Icon(
                        painter = if (selectedItemIndex == index) item.filledIcon else item.outlinedIcon,
                        contentDescription = stringResource(id = item.title),
                        modifier = Modifier.size(24.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = ChosenBottomBarColor
                ),
            )
        }
    }
}