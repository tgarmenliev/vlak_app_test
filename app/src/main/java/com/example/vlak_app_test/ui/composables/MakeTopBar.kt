package com.example.vlak_app_test.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.vlak_app_test.ui.theme.PrimaryDarkColor

@Composable
fun MakeTopBar(
    modifier: Modifier = Modifier,
    titleText: String = "Train",
    haveCancelButton: Boolean = false,
    onCancelButtonPressed: (Int) -> Unit = {}

) {
    Column(
    ) {

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
                    .fillMaxHeight(0.16f)
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .background(color = PrimaryDarkColor)
                    .padding(16.dp)
            )

            Text(
                text = titleText,
                modifier = Modifier
                    .constrainAs(text) {
                        bottom.linkTo(box.bottom)
                        start.linkTo(box.start)
                    }
                    .padding(16.dp),
                style = TextStyle(
                    fontSize = 26.sp,
                    color = Color.White
                )
            )

            if (haveCancelButton) {
                ClickableText(
                    text = AnnotatedString("Cancel"),
                    modifier = Modifier
                        .constrainAs(cancelText) {
                            top.linkTo(box.top)
                            end.linkTo(box.end)
                        }
                        .padding(16.dp),
                    onClick = onCancelButtonPressed,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}