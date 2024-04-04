package com.bultrain.vlak_app_test.ui.bottom_bar

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MakeBottomBar(
    items: List<BottomBarItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit,
    isKeyboardVisible: MutableState<Boolean>
) {

    if (isKeyboardVisible.value) {
        return
    }

    // Listen for navigation changes and update the selected item
    navController.addOnDestinationChangedListener(
        listener = { _, destination, _ ->
            val selectedItem = items.find { it.firstScreenRoute == destination.route }
            val index = items.indexOf(selectedItem)
            if (index != selectedItemIndex) {
                onItemSelected(index)
            }
        }
    )

    NavigationBar(
        modifier = modifier,
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    onItemSelected(index)
                    navController.navigate(item.route)
                },
                label = {
                    Text(
                        text = stringResource(id = item.title),
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(id = (if (selectedItemIndex == index) item.filledIcon else item.outlinedIcon)),
                        contentDescription = stringResource(id = item.title),
                        modifier = Modifier.size(24.dp),
                    )
                },
            )
        }
    }
}