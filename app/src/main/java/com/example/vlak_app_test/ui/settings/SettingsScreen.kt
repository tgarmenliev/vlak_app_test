package com.example.vlak_app_test.ui.settings

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.example.vlak_app_test.R
import com.example.vlak_app_test.datastore.DataStoreManager
import com.example.vlak_app_test.room.UserSettings
import com.example.vlak_app_test.ui.composables.MakeButton
import com.example.vlak_app_test.ui.composables.MakeImageHeader
import com.example.vlak_app_test.ui.composables.MakeSimpleInputField
import com.example.vlak_app_test.ui.error.ErrorScreen
import com.example.vlak_app_test.ui.loading.LoadingScreen
import com.example.vlak_app_test.updateAppLanguage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MakeSettingsScreen(
    onBackButton: () -> Unit,
    dataStoreManager: DataStoreManager
) {
    val darkMode by dataStoreManager.darkModeFlow.collectAsState(initial = false)

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MakeImageHeader(
            text = R.string.settings,
            image = painterResource(id = R.drawable.sett_back),
            modifier = Modifier.height(200.dp)
        )

        DarkModeSwitch(darkMode, dataStoreManager, coroutineScope)

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
        )
    }
}

@Composable
fun DarkModeSwitch(
    darkMode: Boolean?,
    dataStoreManager: DataStoreManager,
    coroutineScope: CoroutineScope
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.dark_mode),
            contentDescription = "Dark Mode",
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = stringResource(id = R.string.dark_mode),
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        )
        Switch(
            checked = darkMode ?: false,
            onCheckedChange = { isChecked ->
                coroutineScope.launch {
                    dataStoreManager.storeDarkMode(isChecked)
                }
            }
        )
    }
}

//@Composable
//fun MakeSettingsScreen(
//    viewModel: SettingsViewModel,
//    onBackButton: () -> Unit,
//    dataStoreManager: DataStoreManager
//) {
//    when(val settingsState = viewModel.settingsState) {
//        is SettingsState.Loading -> {
//            LoadingScreen(modifier = Modifier.fillMaxSize())
//        }
//        is SettingsState.Success -> {
//            var data = viewModel.getSettings()
//            data.theme = when(data.theme) {
//                "light" -> stringResource(id = R.string.theme_light)
//                "dark" -> stringResource(id = R.string.theme_dark)
//                "auto" -> stringResource(id = R.string.theme_system)
//                else -> stringResource(id = R.string.theme_system)
//            }
//            data.language = when(data.language) {
//                "bg" -> stringResource(id = R.string.language_bg)
//                "en" -> stringResource(id = R.string.language_en)
//                "auto" -> stringResource(id = R.string.language_system)
//                else -> stringResource(id = R.string.language_system)
//            }
//            MakeSettingsOnScreen(
//                data = data,
//                onBackButton = onBackButton,
//                onSaveButton = {
//                    theme, language, name ->
//                    viewModel.saveSettings(data.id, theme, language, name)
//                }
//            )
//        }
//        is SettingsState.Error -> {
//            ErrorScreen(error = settingsState.error, modifier = Modifier.fillMaxSize())
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MakeSettingsOnScreen(
//    modifier: Modifier = Modifier,
//    data: UserSettings,
//    onBackButton: () -> Unit,
//    onSaveButton: (String, String, String) -> Unit
//) {
//    val context = LocalContext.current
//
//    var isExpandedTheme by remember {
//        mutableStateOf(false)
//    }
//
//    var theme by remember {
//        mutableStateOf(data.theme)
//    }
//
//    var themeCode by remember {
//        mutableStateOf("")
//    }
//
//    var isExpandedLanguage by remember {
//        mutableStateOf(false)
//    }
//
//    var language by remember {
//        mutableStateOf(data.language)
//    }
//
//    var languageCode by remember {
//        mutableStateOf("default")
//    }
//
//    var name by remember {
//        mutableStateOf(data.name)
//    }
//
//
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState()),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        MakeImageHeader(
//            text = R.string.settings,
//            image = painterResource(id = R.drawable.sett_back),
//            modifier = Modifier.height(200.dp)
//        )
//
//        Row(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(
//                text = stringResource(id = R.string.theme),
//                modifier = Modifier.weight(1f)
//            )
//
//            ExposedDropdownMenuBox(
//                expanded = isExpandedTheme,
//                onExpandedChange = { isExpandedTheme = it },
//                modifier = Modifier.weight(1f)
//            ) {
//                TextField(
//                    value = theme,
//                    onValueChange = {},
//                    readOnly = true,
//                    trailingIcon = {
//                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedTheme)
//                    },
//                    modifier = Modifier.menuAnchor()
//                )
//                ExposedDropdownMenu(
//                    expanded = isExpandedTheme,
//                    onDismissRequest = { isExpandedTheme = false }
//                ) {
//                    DropdownMenuItem(
//                        text = { Text(text = stringResource(id = R.string.theme_light)) },
//                        onClick = {
//                            theme = context.getString(R.string.theme_light)
//                            themeCode = "light"
//                            isExpandedTheme = false
//                        }
//                    )
//
//                    DropdownMenuItem(
//                        text = { Text(text = stringResource(id = R.string.theme_dark)) },
//                        onClick = {
//                            theme = context.getString(R.string.theme_dark)
//                            themeCode = "dark"
//                            isExpandedTheme = false
//                        }
//                    )
//
//                    DropdownMenuItem(
//                        text = { Text(text = stringResource(id = R.string.theme_system)) },
//                        onClick = {
//                            theme = context.getString(R.string.theme_system)
//                            themeCode = "auto"
//                            isExpandedTheme = false
//                        }
//                    )
//                }
//            }
//        }
//
//        Row(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(
//                text = stringResource(id = R.string.language),
//                modifier = Modifier.weight(1f)
//            )
//
//            ExposedDropdownMenuBox(
//                expanded = isExpandedLanguage,
//                onExpandedChange = { isExpandedLanguage = it },
//                modifier = Modifier.weight(1f)
//            ) {
//                TextField(
//                    value = language,
//                    onValueChange = {},
//                    readOnly = true,
//                    trailingIcon = {
//                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedLanguage)
//                    },
//                    modifier = Modifier.menuAnchor()
//                )
//                ExposedDropdownMenu(
//                    expanded = isExpandedLanguage,
//                    onDismissRequest = { isExpandedLanguage = false }
//                ) {
//                    DropdownMenuItem(
//                        text = { Text(text = stringResource(id = R.string.language_bg)) },
//                        onClick = {
//                            language = context.getString(R.string.language_bg)
//                            languageCode = "bg"
//                            isExpandedLanguage = false
//                        }
//                    )
//
//                    DropdownMenuItem(
//                        text = { Text(text = stringResource(id = R.string.language_en)) },
//                        onClick = {
//                            language = context.getString(R.string.language_en)
//                            languageCode = "en"
//                            isExpandedLanguage = false
//                        }
//                    )
//
//                    DropdownMenuItem(
//                        text = { Text(text = stringResource(id = R.string.language_system)) },
//                        onClick = {
//                            language = context.getString(R.string.language_system)
//                            languageCode = "auto"
//                            isExpandedLanguage = false
//                        }
//                    )
//                }
//            }
//        }
//
//        Row(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(
//                text = stringResource(id = R.string.name),
//                modifier = Modifier.weight(1f)
//            )
//
//            MakeSimpleInputField(
//                titleText = name,
//                hintText = R.string.name,
//                keyboardType = KeyboardType.Text,
//                onValueChange = {
//                    name = it
//                },
//                modifier = Modifier.weight(1f)
//            )
//        }
//
//        MakeButton(
//            text = R.string.save,
//            onClick = {
//                onSaveButton(themeCode, languageCode, name)
//            },
//            modifier = Modifier.fillMaxWidth(0.6f)
//        )
//
//        MakeButton(
//            text = R.string.return_to_home,
//            onClick = {
//                onBackButton()
//            },
//            modifier = Modifier
//                .fillMaxWidth(0.6f)
//                .border(
//                    width = 2.dp,
//                    color = MaterialTheme.colorScheme.onBackground,
//                    shape = RoundedCornerShape(16.dp)
//                ),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color.Transparent,
//            )
//        )
//    }
//}
