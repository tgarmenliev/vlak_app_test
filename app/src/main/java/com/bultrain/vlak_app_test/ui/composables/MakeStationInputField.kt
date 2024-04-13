package com.bultrain.vlak_app_test.ui.composables

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.bultrain.vlak_app_test.stationsList
import java.util.Locale

@Composable
fun MakeStationInputField(
    modifier: Modifier = Modifier,
    station: String,
    onStationSelected: (String) -> Unit,
    hintText: Int,
    labelText: Int,
    isKeyboardVisible: MutableState<Boolean>
) {

    val stations = stationsList

    val language = Locale.getDefault().language

    val keyboardController = LocalSoftwareKeyboardController.current

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

    var textFieldValueState by remember {
        mutableStateOf(TextFieldValue(station))
    }

    val isKeyboardOpen by keyboardAsState()

    LaunchedEffect(isKeyboardOpen) {
        isKeyboardVisible.value = isKeyboardOpen == Keyboard.Opened
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
            .background(color = Color.Transparent)
    ) {

        Text(
            modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
            text = stringResource(id = labelText),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            ),
        )

        Column(modifier = Modifier.fillMaxWidth()) {

            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextFields)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        }
                        .onFocusChanged {
                            isKeyboardVisible.value = it.isFocused
                        },
                    value = textFieldValueState,
                    onValueChange = {
                        textFieldValueState = it
                        onStationSelected(it.text)
                        expanded = true
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = hintText),
                            modifier = Modifier.padding(end = 8.dp),
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(
                        fontSize = 16.sp
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        isKeyboardVisible.value = false
                    }),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { onStationSelected("") }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Rounded.Clear,
                                contentDescription = "clear",
                            )
                        }
                    }
                )
            }

            // Create a list of suggestions
            AnimatedVisibility(visible = expanded && station.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .width(textFieldSize.width.dp),
                    shape = RoundedCornerShape(10.dp),
                ) {

                    LazyColumn(
                        modifier = Modifier.heightIn(max = 150.dp),
                    ) {
                        val uniqueSuggestions = HashSet<String>()
                        var name: String

                        val sortedStations = stations.sortedBy { if (language == "bg") it.name else it.englishName }
                        items(sortedStations) { value ->
                            name = if (language == "bg") value.name else value.englishName
                            val startsWithFirstLetters = name.lowercase().startsWith(station.lowercase())
                            if (uniqueSuggestions.add(name) && startsWithFirstLetters) {
                                ItemsCategory(title = name) { title ->
                                    onStationSelected(title)
                                    textFieldValueState = TextFieldValue(
                                        text = title,
                                        selection = TextRange(title.length)
                                    )
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
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium
        )
    }
}