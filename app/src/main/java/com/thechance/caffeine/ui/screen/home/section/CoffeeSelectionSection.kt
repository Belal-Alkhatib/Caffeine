package com.thechance.caffeine.ui.screen.home.section

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thechance.caffeine.R
import com.thechance.caffeine.ui.components.PrimaryButton
import com.thechance.caffeine.ui.components.ProfileActionRow
import com.thechance.caffeine.ui.components.calculateCurrentOffsetForPage
import com.thechance.caffeine.ui.util.getCoffeeImageByType
import com.thechance.caffeine.ui.screen.home.CoffeeType
import com.thechance.caffeine.ui.screen.home.HomeInteraction
import com.thechance.caffeine.ui.screen.home.HomeUiState
import com.thechance.caffeine.ui.theme.Urbanist
import kotlin.math.absoluteValue

@Composable
fun CoffeeSelectionSection(
    state: HomeUiState,
    interaction: HomeInteraction,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileActionRow(modifier = Modifier.padding(horizontal = 16.dp))

        MorningGreeting(modifier = Modifier.padding(horizontal = 16.dp))

        CoffeeTypePager(
            modifier = Modifier.padding(top = 56.dp),
            coffeeTypes = state.coffeeTypes,
            onSelectCoffeeChange = interaction::onSelectCoffeeChange
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            title = stringResource(R.string.continue_to),
            trailingIconPainter = painterResource(R.drawable.ic_arrow_right),
            onClick = interaction::onContinueToPrepareCoffeeClick
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun ColumnScope.MorningGreeting(modifier: Modifier = Modifier, username: String = "Bilal") {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .align(Alignment.Start)
            .padding(top = 16.dp),
        text = stringResource(R.string.good_morning),
        textAlign = TextAlign.Start,
        fontFamily = Urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        color = Color(0xFFB3B3B3)
    )

    Text(
        modifier = modifier
            .fillMaxWidth()
            .align(Alignment.Start),
        text = username,
        textAlign = TextAlign.Start,
        fontFamily = Urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        color = Color(0xFF3B3B3B)
    )

    Text(
        modifier = modifier
            .fillMaxWidth()
            .align(Alignment.Start),
        text = stringResource(R.string.what_would_you_like_to_drink_today),
        textAlign = TextAlign.Start,
        fontFamily = Urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color(0xCC1F1F1F)
    )
}

@Composable
private fun CoffeeTypePager(
    coffeeTypes: List<CoffeeType>,
    onSelectCoffeeChange: (CoffeeType) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { coffeeTypes.size })

    LaunchedEffect(pagerState.currentPage) {
        onSelectCoffeeChange(coffeeTypes[pagerState.currentPage])
    }

    HorizontalPager(
        modifier = modifier.fillMaxWidth(),
        state = pagerState,
        verticalAlignment = Alignment.CenterVertically,
        reverseLayout = true,
        pageSpacing = (-180).dp
    ) { coffeeTypeIndex ->
        val pageOffset = pagerState.calculateCurrentOffsetForPage(coffeeTypeIndex)
        val targetScale = 1f - pageOffset.coerceIn(-0.33f, 0.33f).absoluteValue

        val animatedScale by animateFloatAsState(
            targetValue = targetScale,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            ),
            label = "CoffeeCupScaleAnimation"
        )

        val textScale by animateFloatAsState(
            targetValue = if (pagerState.currentPage == coffeeTypeIndex) 1f else 0.75f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            ),
            label = "TextScaleAnimation"
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = animatedScale
                        scaleY = animatedScale
                    }
                    .height(298.dp),
                contentScale = ContentScale.Crop,
                painter = getCoffeeImageByType(coffeeTypes[coffeeTypeIndex]),
                contentDescription = coffeeTypes[coffeeTypeIndex].name
            )

            Text(
                modifier = Modifier.graphicsLayer {
                    scaleX = textScale
                    scaleY = textScale
                },
                text = coffeeTypes[coffeeTypeIndex].name.lowercase()
                    .replaceFirstChar { it.uppercase() },
                fontFamily = Urbanist,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = Color(0xFF1F1F1F)
            )
        }
    }
}