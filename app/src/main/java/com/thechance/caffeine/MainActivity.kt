package com.thechance.caffeine

import android.R.attr.path
import android.R.attr.y
import android.os.Bundle
import android.util.Log.i
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.thechance.caffeine.ui.screen.home.HomeScreen
import com.thechance.caffeine.ui.theme.CaffeineTheme
import java.nio.file.Files.size
import kotlin.math.sin
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.util.lerp
import com.thechance.caffeine.ui.screen.home.section.SymmetricSlowInSlowOutEasing
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
        setContent {
            CaffeineTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    HomeScreen()
                }
            }
        }
    }
}
@Composable
fun SmoothGrowingWavyLine() {
    val waveLength = 220f
    val waveAmplitude = 40f

    val animatedHead = remember { Animatable(0f) }

    val screenWidth = LocalDensity.current.run { LocalConfiguration.current.screenWidthDp.dp.toPx() }

    LaunchedEffect(Unit) {
        animatedHead.animateTo(
            targetValue = screenWidth,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 4000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.White)
            .drawWithCache {
                val centerY = size.height / 2
                val headOffsetX = animatedHead.value
                val firstWaveDip = -40f

                onDrawBehind {
                    val path = Path().apply {
                        moveTo(0f, centerY + firstWaveDip) // Lower first wave

                        var x = 10f
                        while (x <= headOffsetX) {
                            val isFirst = x == 0f

                            val yBase = when {
                                isFirst -> centerY + firstWaveDip
                                else -> centerY
                            }

                            quadraticTo(
                                x + waveLength / 4, yBase - waveAmplitude,
                                x + waveLength / 2, yBase
                            )
                            quadraticTo(
                                x + 3 * waveLength / 4, yBase + waveAmplitude,
                                x + waveLength, yBase
                            )
                            x += waveLength
                        }
                    }

                    drawPath(
                        path = path,
                        color = Color(0xFF7C351B),
                        style = Stroke(width = 6f)
                    )
                }
            }
    )
}

@Composable
fun SmoothGrowingWavyLine2() {
    val waveLength = 220f
    val waveAmplitude = 40f

    // Animation for the line drawing progress
    val animatedHead = remember { Animatable(0f) }
    // Animation for the drawing point (will follow the head with a slight delay)
    val drawingPoint = remember { Animatable(0f) }

    val screenWidth = LocalDensity.current.run { LocalConfiguration.current.screenWidthDp.dp.toPx() }

    LaunchedEffect(Unit) {
        // Animate the main line drawing
        animatedHead.animateTo(
            targetValue = screenWidth,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 4000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        // Animate the drawing point to follow the head with a slight delay
        launch {
            animatedHead.snapTo(0f)
            drawingPoint.snapTo(0f)
            animatedHead.animateTo(screenWidth, animationSpec = tween(4000, easing = FastOutSlowInEasing))

            while (true) {
                // Follow the head with a small delay to make it look like a drawing point
                drawingPoint.animateTo(
                    targetValue = animatedHead.value,
                    animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
                )
                delay(16) // Roughly 60fps update
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.White)
            .drawWithCache {
                val centerY = size.height / 2
                val headOffsetX = animatedHead.value
                val drawingPointX = drawingPoint.value
                val firstWaveDip = -40f

                onDrawBehind {
                    // Draw the main path
                    val path = Path().apply {
                        moveTo(0f, centerY + firstWaveDip) // Lower first wave

                        var x = 10f
                        while (x <= headOffsetX) {
                            val isFirst = x == 0f

                            val yBase = when {
                                isFirst -> centerY + firstWaveDip
                                else -> centerY
                            }

                            quadraticTo(
                                x + waveLength / 4, yBase - waveAmplitude,
                                x + waveLength / 2, yBase
                            )
                            quadraticTo(
                                x + 3 * waveLength / 4, yBase + waveAmplitude,
                                x + waveLength, yBase
                            )
                            x += waveLength
                        }
                    }

                    // Draw the main line
                    drawPath(
                        path = path,
                        color = Color(0xFF000000),
                        style = Stroke(width = 6f)
                    )

                    // Draw the drawing point (only if we're actually drawing)
                    if (animatedHead.value > 0 && animatedHead.value < screenWidth) {
                        // Calculate the y position at the drawing point
                        val pointY = calculateYAtX(drawingPointX, centerY, waveLength, waveAmplitude, firstWaveDip)

                        // Draw a circle at the drawing point
                        drawCircle(
                            color = Color.Red,
                            radius = 10f,
                            center = Offset(drawingPointX, pointY)
                        )
                    }
                }
            }
    )
}

// Helper function to calculate the Y position at a given X coordinate along the wave
private fun calculateYAtX(x: Float, centerY: Float, waveLength: Float, amplitude: Float, firstWaveDip: Float): Float {
    if (x <= 0) return centerY + firstWaveDip

    val segment = (x % waveLength) / waveLength
    val yBase = if (x < waveLength) centerY + firstWaveDip else centerY

    return when {
        segment < 0.25 -> {
            val t = segment / 0.25f
            val controlY = yBase - amplitude
            lerp(yBase, controlY, t)
        }
        segment < 0.5 -> {
            val t = (segment - 0.25f) / 0.25f
            val controlY = yBase - amplitude
            lerp(controlY, yBase, t)
        }
        segment < 0.75 -> {
            val t = (segment - 0.5f) / 0.25f
            val controlY = yBase + amplitude
            lerp(yBase, controlY, t)
        }
        else -> {
            val t = (segment - 0.75f) / 0.25f
            val controlY = yBase + amplitude
            lerp(controlY, yBase, t)
        }
    }
}









