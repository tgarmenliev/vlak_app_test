package com.example.vlak_app_test.ui.top_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.vlak_app_test.R

@Composable
fun MakeTopBar(
    modifier: Modifier = Modifier,
    titleText: Int = R.string.schedule,
    haveCancelButton: Boolean = false,
    onCancelButtonPressed: () -> Unit = {},
) {
    Column{

        ConstraintLayout(
            modifier
        ) {
            val (box, cancelText, text) = createRefs()

            Box(
                modifier = Modifier
                    .constrainAs(box) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .fillMaxHeight(0.12f)
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .padding(16.dp)
            )

            Text(
                text = stringResource(id = titleText),
                modifier = Modifier
                    .constrainAs(text) {
                        start.linkTo(cancelText.end)
                        top.linkTo(box.top)
                    }
                    .padding(top = 12.dp, bottom = 16.dp, end = 16.dp),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )


            if (haveCancelButton) {
                IconButton(
                    onClick = onCancelButtonPressed,
                    modifier = Modifier
                        .constrainAs(cancelText) {
                            top.linkTo(box.top)
                            start.linkTo(box.start)
                        }
                        .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 4.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "back",
                        tint = Color.White,
                    )
                }
            }
        }
    }
}