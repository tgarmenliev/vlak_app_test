package com.example.vlak_app_test.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.vlak_app_test.stationsList
import com.example.vlak_app_test.ui.theme.BottomBarContainerColor
import java.util.Locale

@Composable
fun MakeStationInputField(
    modifier: Modifier = Modifier,
    station: String,
    onStationSelected: (String) -> Unit,
    hintText: Int,
    labelText: Int
) {

    val stations = stationsList


//    var category by remember {
//        mutableStateOf("")
//    }

    val language = Locale.getDefault().language

    val heightTextFields by remember {
        mutableStateOf(55.dp)
    }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    expanded = false
                }
            )
    ) {

        Text(
            modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
            text = stringResource(id = labelText),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black,
                fontWeight = FontWeight.Medium
            ),
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )

        Column(modifier = Modifier.fillMaxWidth()) {

            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextFields)
                        .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    value = station,
                    onValueChange = {
                        onStationSelected(it)
                        expanded = true
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = hintText),
                            modifier = Modifier.padding(end = 8.dp),
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = Color.Gray,
                            )
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Rounded.KeyboardArrowDown,
                                contentDescription = "arrow",
                                tint = Color.Black
                            )
                        }
                    }
                )
            }

            AnimatedVisibility(visible = expanded) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .width(textFieldSize.width.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = BottomBarContainerColor,
                        contentColor = Color.Black
                    ),
                ) {

                    LazyColumn(
                        modifier = Modifier.heightIn(max = 150.dp),
                    ) {
                        val uniqueSuggestions = HashSet<String>()
                        var name: String = ""

                        if (station.isNotEmpty()) {
                            items(stations) { value ->
                                name = if (language == "bg") value.name else value.englishName
                                if (name.lowercase().contains(station.lowercase())) {
                                    if (uniqueSuggestions.add(name)) {
                                        ItemsCategory(title = name) { title ->
                                            onStationSelected(title)
                                            expanded = false
                                        }
                                    }
                                }
                            }
                        } else {
                            val sortedStations = stations.sortedBy { if (language == "bg") it.name else it.englishName }
                            items(sortedStations) { value ->
                                name = if (language == "bg") value.name else value.englishName
                                if (uniqueSuggestions.add(name)) {
                                    ItemsCategory(title = name) { title ->
                                        onStationSelected(title)
                                        expanded = false
                                    }
                                }
                            }
                        }
                    }


                }
            }

        }

    }


}

@Composable
fun ItemsCategory(
    title: String,
    onSelect: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelect(title)
                keyboardController?.hide()
            }
            .padding(10.dp)
    ) {
        Text(text = title, fontSize = 16.sp)
    }
}