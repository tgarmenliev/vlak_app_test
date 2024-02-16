package com.example.vlak_app_test.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.vlak_app_test.R

@Composable
fun MakeSimpleInputField(
    modifier: Modifier = Modifier,
    titleText: String,
    hintText: Int,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit,
    labelText: Int = R.string.empty_string,
) {
    Column(
        modifier
    ) {

        Text(
            modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
            text = stringResource(id = labelText),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            ),
            fontWeight = FontWeight.Medium
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(16.dp)
                ),
            value = titleText,
            onValueChange = onValueChange,
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
                keyboardType = keyboardType,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = "clear",
                    )
                }
            }
        )

//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .border(
//                    width = 2.dp,
//                    color = MaterialTheme.colorScheme.onBackground,
//                    shape = RoundedCornerShape(16.dp)
//                )
//                .padding(horizontal = 16.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                BasicTextField(
//                    value = titleText,
//                    onValueChange = onValueChange,
//                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
//                    singleLine = true,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 16.dp, bottom = 16.dp),
//                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
//                    textStyle = MaterialTheme.typography.labelLarge.copy(
//                        color = MaterialTheme.colorScheme.onSurface,
//                    ),
//                    decorationBox = { innerTextField ->
//                        Row {
//                            if (titleText.isEmpty()) {
//                                Text(
//                                    text = stringResource(id = hintText),
//                                    modifier = Modifier.padding(end = 8.dp),
//                                    style = MaterialTheme.typography.labelLarge.copy(
//                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
//                                    )
//                                )
//                            }
//                            innerTextField()
//                            if (titleText.isNotEmpty()) {
//                                IconButton(
//                                    onClick = { onValueChange("") },
//                                    modifier = Modifier.padding(end = 8.dp)
//                                ) {
//                                    Icon(
//                                        imageVector = Icons.Rounded.Clear,
//                                        contentDescription = "clear text"
//                                    )
//                                }
//                            }
//                        }
//                    }
//                )
//            }
//        }
    }
}
