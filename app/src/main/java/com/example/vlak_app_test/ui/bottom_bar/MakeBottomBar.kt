package com.example.vlak_app_test.ui.bottom_bar

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vlak_app_test.ui.theme.BottomBarContainerColor
import com.example.vlak_app_test.ui.theme.ChosenBottomBarColor

@Composable
fun MakeBottomBar(
    items: List<BottomBarItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit
) {

    NavigationBar(
        containerColor = BottomBarContainerColor
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    onItemSelected(index)
                    navController.navigate(item.route)
                },
                label = {
                    Text(text = stringResource(id = item.title))
                },
                icon = {
                    Icon(
                        painter = painterResource(id = (if (selectedItemIndex == index) item.filledIcon else item.outlinedIcon)),
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