package com.thechance.caffeine.ui.screen.home.section

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.thechance.caffeine.R
import com.thechance.caffeine.ui.components.PrimaryButton
import com.thechance.caffeine.ui.components.dropShadow
import com.thechance.caffeine.ui.screen.home.CoffeeCupSize
import com.thechance.caffeine.ui.screen.home.CoffeeStrength
import com.thechance.caffeine.ui.screen.home.CoffeeStrengthAnimationState
import com.thechance.caffeine.ui.screen.home.HomeInteraction
import com.thechance.caffeine.ui.screen.home.HomeUiState
import com.thechance.caffeine.ui.theme.Urbanist
import com.thechance.caffeine.ui.util.getMlByCoffeeCupSize
import org.koin.core.context.startKoin


@Composable
fun CoffeeCustomizationSection(
    state: HomeUiState,
    interaction: HomeInteraction,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {


        CoffeeCustomizationActionBar(
            state = state,
            actionBarTitle = state.selectedCoffeeType.name.lowercase()
                .replaceFirstChar { it.uppercase() })


        AnimatedCoffeeBeans(state = state)

        CoffeeCup(state = state)

        val coffeeSelectorsAlpha by animateFloatAsState(
            targetValue = if (state.isCoffeeSelectorsVisible) 1f else 0f,
            animationSpec = tween(700),
            label = "Coffee Selectors alpha"
        )

        Spacer(modifier = Modifier.weight(1f))
        Column(modifier = Modifier.alpha(coffeeSelectorsAlpha), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            CoffeeCupSelector(state = state, interaction = interaction)
            CoffeeStrengthSelector(state = state, interaction = interaction)
        }

        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(
            modifier = Modifier.alpha(coffeeSelectorsAlpha),
            title = stringResource(R.string.continue_to),
            trailingIconPainter = painterResource(R.drawable.ic_arrow_right),
            onClick = interaction::onContinueToDoneCoffeeClick
        )
        Spacer(modifier = Modifier.weight(2f))
    }

}

@Composable
private fun CoffeeCustomizationActionBar(state: HomeUiState, actionBarTitle: String, modifier: Modifier = Modifier) {
    val  actionBarCoffeeTransaction by animateFloatAsState(
        targetValue = if(state.isCoffeeSelectorsVisible) 0f else -400f,
        animationSpec = tween(700),
        label = "Coffee action bar y transaction"
    )
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 56.dp)
            .graphicsLayer{
                translationY = actionBarCoffeeTransaction
            },
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .background(color = Color(0xFFF5F5F5), shape = CircleShape)
                .padding(12.dp)
                .size(24.dp)
                .rotate(180f),
            painter = painterResource(R.drawable.ic_arrow_right),
            tint = Color(0xDE1F1F1F),
            contentDescription = "add button"
        )

        Text(
            text = actionBarTitle,
            textAlign = TextAlign.Start,
            fontFamily = Urbanist,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color(0xDE1F1F1F)
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun CoffeeCup(state: HomeUiState) {
    val animatedCupScale by animateFloatAsState(
        targetValue = when (state.selectedCoffeeCupSize) {
            CoffeeCupSize.SMALL -> .7f
            CoffeeCupSize.MEDIUM -> 1f
            CoffeeCupSize.LARGE -> 1.3f
        },
        animationSpec = spring(),
        label = "animatedCupScale",
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Box(modifier = Modifier.align(Alignment.TopCenter), contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier
                    .scale(animatedCupScale),
                contentScale = ContentScale.Crop,
                painter = painterResource(R.drawable.img_empty_cup),
                contentDescription = state.selectedCoffeeType.name
            )

            Image(
                //modifier = Modifier.scale(animatedCupScale),
                contentScale = ContentScale.Crop,
                painter = painterResource(R.drawable.img_the_chance_coffee_logo),
                contentDescription = state.selectedCoffeeType.name
            )
        }

        AnimatedContent(
            targetState = state.selectedCoffeeCupSize,
            transitionSpec = {
                val direction = if (targetState.ordinal > initialState.ordinal) 1 else -1

                slideInVertically(animationSpec = tween(700, easing = SymmetricSlowInSlowOutEasing))
                { height -> direction * height } + fadeIn() togetherWith
                        slideOutVertically(
                            animationSpec = tween(
                                700,
                                easing = SymmetricSlowInSlowOutEasing
                            )
                        )
                        { height -> -direction * height } + fadeOut()
            },
            label = "coffee size text transition",
        ) { targetCup ->
            Text(
                text = getMlByCoffeeCupSize(targetCup),
                textAlign = TextAlign.Start,
                fontFamily = Urbanist,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color(0x99000000),
                modifier = Modifier.align(Alignment.TopStart)
            )
        }
    }
}

@Composable
private fun AnimatedCoffeeBeans(state: HomeUiState) {

    val transition = updateTransition(
        targetState = state.coffeeStrengthAnimationState,
        label = "BeansTransition"
    )

    val beansOffsetY by transition.animateDp(
        transitionSpec = { tween(1800, easing = FastOutSlowInEasing) },
        label = "BeansOffsetY"
    ) { animationState ->
        when (animationState) {
            CoffeeStrengthAnimationState.ENTERING -> 110.dp
            CoffeeStrengthAnimationState.EXITING -> (-600).dp
            CoffeeStrengthAnimationState.NONE -> (-600).dp
        }
    }

    val beansScale by transition.animateFloat(
        transitionSpec = { tween(1800, easing = FastOutSlowInEasing) },
        label = "BeansScale"
    ) { animationState ->
        when (animationState) {
            CoffeeStrengthAnimationState.ENTERING -> 0f
            CoffeeStrengthAnimationState.EXITING -> 6f
            CoffeeStrengthAnimationState.NONE -> 6f
        }
    }

    val beansAlpha by transition.animateFloat(
        transitionSpec = { tween(1800, easing = FastOutSlowInEasing) },
        label = "BeansAlpha"
    ) { animationState ->
        when (animationState) {
            CoffeeStrengthAnimationState.ENTERING -> 0.7f
            CoffeeStrengthAnimationState.EXITING -> 1f
            CoffeeStrengthAnimationState.NONE -> 1f
        }
    }


    Image(
        painter = painterResource(R.drawable.img_beans),
        contentDescription = state.selectedCoffeeType.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .graphicsLayer {
                translationY = beansOffsetY.toPx()
                scaleX = beansScale
                scaleY = beansScale
                alpha = beansAlpha
            }
            .zIndex(-1f)
    )

}

@Composable
private fun CoffeeCupSelector(state: HomeUiState, interaction: HomeInteraction) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .background(color = Color(0xFFF5F5F5), shape = CircleShape)
            .padding(all = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CoffeeCupSize.entries.forEach { size ->
            val isSelected = size == state.selectedCoffeeCupSize
            val animatedBackground by animateColorAsState(
                targetValue = if (isSelected) Color(0xFF7C351B) else Color.Transparent,
                animationSpec = tween(400),
                label = "animatedCupSizeBackground",
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .then(
                        if (isSelected) Modifier.dropShadow(color = Color(0x80B94B23), blur = 16.dp)
                        else Modifier
                    )
                    .clip(CircleShape)
                    .clickable(
                        enabled = state.isCoffeeSelectorsVisible,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { interaction.onSelectCoffeeCupSizeChange(size) },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color = animatedBackground)
                        .align(Alignment.CenterStart)
                )

                Text(
                    text = "${size.name.first()}",
                    textAlign = TextAlign.Center,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = if (isSelected) Color.White.copy(alpha = .87f) else Color(0x991F1F1F)
                )
            }

        }
    }
}

@Composable
private fun CoffeeStrengthSelector(state: HomeUiState, interaction: HomeInteraction) {
    Column(
        modifier = Modifier.width(intrinsicSize = IntrinsicSize.Max),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .background(color = Color(0xFFF5F5F5), shape = CircleShape)
                .padding(all = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoffeeStrength.entries.forEach { strength ->
                val isSelected = strength == state.selectedCoffeeStrength
                val animatedBackground by animateColorAsState(
                    targetValue = if (isSelected) Color(0xFF7C351B) else Color.Transparent,
                    animationSpec = tween(400),
                    label = "animatedCoffeeStrengthBackground",
                )
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .then(
                            if (isSelected) Modifier.dropShadow(
                                color = Color(0x80B94B23),
                                blur = 16.dp
                            )
                            else Modifier
                        )
                        .clip(CircleShape)
                        .clickable(
                            enabled = state.isCoffeeSelectorsVisible,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { interaction.onSelectCoffeeStrengthChange(strength) },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(color = animatedBackground)
                            .align(Alignment.CenterStart)
                    )

                    Icon(
                        painter = painterResource(R.drawable.ic_coffee_beans),
                        contentDescription = strength.name,
                        tint = if (isSelected) Color.White.copy(alpha = .87f) else Color.Transparent

                    )
                }

            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoffeeStrength.entries.forEach { strength ->
                Text(
                    text = strength.name.lowercase().replaceFirstChar { it.uppercase() },
                    textAlign = TextAlign.Center,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp,
                    color = Color(0x991F1F1F)
                )
            }
        }
    }
}