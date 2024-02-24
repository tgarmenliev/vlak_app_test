package com.example.vlak_app_test.ui.settings

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vlak_app_test.R
import com.example.vlak_app_test.datastore.DataStoreManager
import com.example.vlak_app_test.ui.composables.MakeButton
import com.example.vlak_app_test.ui.composables.MakeImageHeader
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
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(16.dp)
                ),
            text = R.string.return_to_home,
            onClick = {
                onBackButton()
            },
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
