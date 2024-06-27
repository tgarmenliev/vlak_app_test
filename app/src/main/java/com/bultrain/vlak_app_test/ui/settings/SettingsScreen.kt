package com.bultrain.vlak_app_test.ui.settings

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bultrain.vlak_app_test.R
import com.bultrain.vlak_app_test.datastore.DataStoreManager
import com.bultrain.vlak_app_test.ui.composables.MakeButton
import com.bultrain.vlak_app_test.ui.composables.MakeImageHeader
import com.bultrain.vlak_app_test.ui.language.LocaleManager
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

        Text(
            text = stringResource(id = R.string.app_theme),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 2.dp)
        )

        DarkModeSwitch(darkMode, dataStoreManager, coroutineScope)

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
                .padding(vertical = 10.dp, horizontal = 16.dp)
        )

        Text(
            text = stringResource(id = R.string.change_language),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 2.dp)
        )

        Text(
            text = stringResource(id = R.string.language_warning),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Gray
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LanguageSelectionScreen()

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
                .padding(vertical = 10.dp, horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        MakeButton(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(16.dp)
                ),
            text = R.string.return_to_home,
        ) {
            onBackButton()
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun LanguageSelectionScreen() {
    val context = LocalContext.current
    val localeManager = LocaleManager(context)

    Column {
        MakeButton(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                .align(Alignment.CenterHorizontally),
            text = R.string.en_language,
            onClick = {
                if (localeManager.language == "en") return@MakeButton
                localeManager.language = "en"
                recreateActivity(context as Activity)
            }
        )

        MakeButton(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, bottom = 16.dp, top = 8.dp)
                .align(Alignment.CenterHorizontally),
            text = R.string.bg_language,
            onClick = {
                if (localeManager.language == "bg") return@MakeButton
                localeManager.language = "bg"
                recreateActivity(context as Activity)
            }
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

fun recreateActivity(activity: Activity) {
    val intent = activity.intent
    activity.finish()
    activity.startActivity(intent)
}
