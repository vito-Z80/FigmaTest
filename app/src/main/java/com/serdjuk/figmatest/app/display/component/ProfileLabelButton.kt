package com.serdjuk.figmatest.app.display.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serdjuk.figmatest.R
import com.serdjuk.figmatest.ui.theme.BlackColor
import com.serdjuk.figmatest.ui.theme.CircleColor


@Composable
fun ProfileLabelButton(
    leftIconId: Int? = null,
    rightIconId: Int? = null,
    rightString: String? = null,
    text: String,
    clickable: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(16f.dp)
            .clickable { clickable.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        leftIconId?.let {
            Box() {
                Icon(
                    painter = painterResource(id = R.drawable.ellipse_25),
                    contentDescription = null,
                    tint = CircleColor
                )
                Icon(
                    painter = painterResource(id = it), contentDescription = null,
                    modifier = Modifier.align(Alignment.Center),
                    tint = BlackColor
                )
            }
        }
        Text(
            text = text,
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 14f.sp,
                color = BlackColor,
            ),
            modifier = Modifier.padding(start = 6f.dp),
        )
        Spacer(modifier = Modifier.weight(1f))
        rightIconId?.let {
            Icon(
                painter = painterResource(id = it),
                contentDescription = null,
                tint = BlackColor
            )
        }
        rightString?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Medium,
                fontSize = 14f.sp,
                color = BlackColor
            )
        }
    }
}