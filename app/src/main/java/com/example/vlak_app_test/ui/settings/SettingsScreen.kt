package com.example.vlak_app_test.ui.settings

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.vlak_app_test.R
import com.example.vlak_app_test.room.UserSettings
import com.example.vlak_app_test.ui.composables.MakeButton
import com.example.vlak_app_test.ui.composables.MakeImageHeader
import com.example.vlak_app_test.ui.composables.MakeSimpleInputField
import com.example.vlak_app_test.ui.error.ErrorScreen
import com.example.vlak_app_test.ui.loading.LoadingScreen

@Composable
fun MakeSettingsScreen(
    viewModel: SettingsViewModel,
    onBackButton: () -> Unit,
) {
    when(val settingsState = viewModel.settingsState) {
        is SettingsState.Loading -> {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        }
        is SettingsState.Success -> {
            MakeSettingsOnScreen(
                data = viewModel.getSettings(),
                onBackButton = onBackButton,
                onSaveButton = {
                    theme, language, name ->
                    viewModel.saveSettings(theme, language, name)
                }
            )
        }
        is SettingsState.Error -> {
            ErrorScreen(error = settingsState.error, modifier = Modifier.fillMaxSize())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeSettingsOnScreen(
    modifier: Modifier = Modifier,
    data: UserSettings,
    onBackButton: () -> Unit,
    onSaveButton: (String, String, String) -> Unit
) {
    var isExpandedTheme by remember {
        mutableStateOf(false)
    }

    var theme by remember {
        mutableStateOf(data.theme)
    }

    var isExpandedLanguage by remember {
        mutableStateOf(false)
    }

    var language by remember {
        mutableStateOf(data.language)
    }

    var name by remember {
        mutableStateOf(data.name)
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MakeImageHeader(
            text = R.string.settings,
            image = painterResource(id = R.drawable.sett_back),
            modifier = Modifier.height(200.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.theme),
                modifier = Modifier.weight(1f)
            )

            ExposedDropdownMenuBox(
                expanded = isExpandedTheme,
                onExpandedChange = { isExpandedTheme = it },
                modifier = Modifier.weight(1f)
            ) {
                TextField(
                    value = language,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedLanguage)
                    }
                )
                ExposedDropdownMenu(
                    expanded = isExpandedLanguage,
                    onDismissRequest = { isExpandedLanguage = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = R.string.theme_light)) },
                        onClick = { theme = "light"; isExpandedTheme = false }
                    )

                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = R.string.theme_dark)) },
                        onClick = { theme = "dark"; isExpandedTheme = false }
                    )

                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = R.string.theme_system)) },
                        onClick = { theme = "auto"; isExpandedTheme = false }
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.language),
                modifier = Modifier.weight(1f)
            )

            ExposedDropdownMenuBox(
                expanded = isExpandedLanguage,
                onExpandedChange = { isExpandedLanguage = it },
                modifier = Modifier.weight(1f)
            ) {
                TextField(
                    value = language,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedLanguage)
                    }
                )
                ExposedDropdownMenu(
                    expanded = isExpandedLanguage, 
                    onDismissRequest = { isExpandedLanguage = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = R.string.language_bg)) },
                        onClick = { theme = "bg"; isExpandedLanguage = false }
                    )

                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = R.string.language_en)) },
                        onClick = { theme = "en"; isExpandedLanguage = false }
                    )

                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = R.string.language_system)) },
                        onClick = { theme = "auto"; isExpandedLanguage = false }
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.name),
                modifier = Modifier.weight(1f)
            )

            MakeSimpleInputField(
                titleText = name,
                hintText = R.string.name,
                keyboardType = KeyboardType.Text,
                onValueChange = {
                    name = it
                },
                modifier = Modifier.weight(1f)
            )
        }

        MakeButton(
            text = R.string.save,
            onClick = {
                onSaveButton(theme, language, name)
            },
            modifier = Modifier.fillMaxWidth(0.6f)
        )

        MakeButton(
            text = R.string.return_to_home,
            onClick = {
                onBackButton()
            },
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(16.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
            )
        )
    }
}