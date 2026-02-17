package com.myapp.duelvault.utils.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarGeneral(
    name: String,
    iconLeft: Int? = null,
    onClickIconLeft: () -> Unit = {},
    contentRight: @Composable () -> Unit = {}
) {
    Surface(shadowElevation = 4.dp) {
        TopAppBar(
            title = {
                Text(
                    text = name,
                    modifier = Modifier.padding(end = 10.dp),
                    textAlign = TextAlign.End,
                    fontSize = 16.sp
                )
            }, navigationIcon = {
                if (iconLeft != null) {
                    IconButton(onClick = { onClickIconLeft.invoke() }) {
                        Icon(
                            painter = painterResource(iconLeft),
                            contentDescription = "Icon_left",
                        )
                    }
                }

            }, actions = {
                Row(modifier = Modifier.padding(end = 8.dp)) {
                    contentRight()
                }
            }, colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
        )
    }
}