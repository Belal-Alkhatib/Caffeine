package com.thechance.caffeine.ui.components

import android.R.attr.text
import android.graphics.drawable.Icon
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thechance.caffeine.R
import com.thechance.caffeine.ui.theme.Singlet
import androidx.compose.runtime.getValue
import com.thechance.caffeine.ui.theme.Urbanist

@Composable
fun PrimaryButton(
    title: String,
    trailingIconPainter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mutableInteractionSource = remember { MutableInteractionSource() }
    val pressed by mutableInteractionSource.collectIsPressedAsState()
    val shadowColorAlpha by animateFloatAsState(
        targetValue = if (pressed) .08f else .24f,
        label = "shadowColorAlpha"
    )

    Row(
        modifier = modifier
            .height(56.dp)
            .dropShadow(
                shape = CircleShape,
                color = Color.Black.copy(alpha = shadowColorAlpha),
                offsetY = 6.dp,
                blur = 12.dp
            )
            .background(color = Color(0xFF1F1F1F), shape = CircleShape)
            .padding(horizontal = 32.dp)
            .clickable(
                interactionSource = mutableInteractionSource,
                indication = null
            ) { onClick() },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            textAlign = TextAlign.Center,
            fontFamily = Urbanist,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White.copy(alpha = .87f)
        )

        Icon(
            modifier = Modifier.size(24.dp),
            painter = trailingIconPainter,
            contentDescription = title,
            tint = Color.White.copy(alpha = .87f)
        )
    }
}

@Preview(showSystemUi = false, showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PrimaryButtonPreview() {
    PrimaryButton(
        title = stringResource(R.string.bring_my_coffee),
        trailingIconPainter = painterResource(R.drawable.ic_coffee_cup),
        onClick = {}
    )
}