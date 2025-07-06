package com.thechance.caffeine.ui.screen.home.section

import android.R.attr.translationY
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import com.thechance.caffeine.R
import com.thechance.caffeine.ui.components.PrimaryButton
import com.thechance.caffeine.ui.components.ProfileActionRow
import com.thechance.caffeine.ui.screen.home.HomeInteraction
import com.thechance.caffeine.ui.theme.Singlet

private val SymmetricSlowInSlowOutEasing: Easing = CubicBezierEasing(0.42f, 0.0f, 0.58f, 1.0f)

@Composable
fun WelcomeSection(interaction: HomeInteraction, modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "GhostFloatingTransition")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp)
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ProfileActionRow(modifier = Modifier.padding(bottom = 24.dp))

        FlashingStarsText(
            modifier = Modifier.padding(bottom = 48.dp),
            text = stringResource(R.string.hocus_pocus_i_need_coffee_to_focus),
            infiniteTransition = infiniteTransition
        )
        AnimatedGhostImage(infiniteTransition = infiniteTransition)

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            title = stringResource(R.string.bring_my_coffee),
            trailingIconPainter = painterResource(R.drawable.ic_coffee_cup),
            onClick = interaction::onBringCoffeeClick
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun FlashingStarsText(modifier: Modifier = Modifier, text: String, infiniteTransition: InfiniteTransition) {
    val starsColor by infiniteTransition.animateColor(
        initialValue = Color(0xDE1F1F1F),
        targetValue = Color(0x001F1F1F),
        animationSpec = InfiniteRepeatableSpec(tween(850), repeatMode = RepeatMode.Reverse),
        label = "FlashingStarsColor"
    )
    val starsRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 180f,
        animationSpec = InfiniteRepeatableSpec(tween(850), repeatMode = RepeatMode.Reverse),
        label = "FlashingStarsRotation"
    )

    val starsScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(850), RepeatMode.Reverse),
        label = "FlashingStarsTextScale"
    )

    Box(modifier = modifier.height(IntrinsicSize.Max)) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontFamily = Singlet,
            fontWeight = FontWeight.Normal,
            fontSize = 32.sp,
            lineHeight = 50.sp,
            color = Color(0xDE1F1F1F)
        )

        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 10.dp, bottom = 52.dp)
                .rotate(starsRotation)
                .scale(starsScale),
            painter = painterResource(R.drawable.ic_star),
            tint = starsColor,
            contentDescription = null
        )

        Icon(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 4.dp)
                .rotate(starsRotation)
                .scale(starsScale),
            painter = painterResource(R.drawable.ic_star),
            tint = starsColor,
            contentDescription = null
        )

        Icon(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .rotate(starsRotation)
                .scale(starsScale),
            painter = painterResource(R.drawable.ic_star),
            tint = starsColor,
            contentDescription = null
        )
    }
}

@Composable
private fun ColumnScope.AnimatedGhostImage(infiniteTransition: InfiniteTransition) {
    val ghostY = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -80f,
        animationSpec = InfiniteRepeatableSpec(
            tween(
                durationMillis = 1700,
                easing = SymmetricSlowInSlowOutEasing
            ), repeatMode = RepeatMode.Reverse
        ),
        label = "AnimatedGhostImage"
    )

    Image(
        modifier = Modifier.graphicsLayer {
            translationY = ghostY.value
        },
        painter = painterResource(R.drawable.img_ghost),
        contentDescription = null,
    )

    AnimatedGhostShadow(infiniteTransition = infiniteTransition)
}

@Composable
private fun AnimatedGhostShadow(infiniteTransition: InfiniteTransition) {
    val shadowColor by infiniteTransition.animateColor(
        initialValue = Color.Black.copy(alpha = .16f),
        targetValue = Color.Black.copy(alpha = .08f),
        animationSpec = InfiniteRepeatableSpec(tween(1700), repeatMode = RepeatMode.Reverse),
        label = "AnimatedGhostShadowColor"
    )

    val shadowScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = .80f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 1700,
                easing = SymmetricSlowInSlowOutEasing
            ), RepeatMode.Reverse
        ),
        label = "ShadowScale"
    )

    Canvas(
        modifier = Modifier
            .size(width = 160.dp, height = 28.dp)
            .scale(scaleX = shadowScale, scaleY = shadowScale + .10f)
            .blur(12.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
    ) {
        drawOval(color = shadowColor)
    }
}