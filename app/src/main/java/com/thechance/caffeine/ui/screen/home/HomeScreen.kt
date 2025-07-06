package com.thechance.caffeine.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.thechance.caffeine.ui.screen.home.section.CoffeeSelectionSection
import com.thechance.caffeine.ui.screen.home.section.WelcomeSection
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    HomeContent(state = state, interaction = viewModel)
}

@Composable
fun HomeContent(state: HomeUiState, interaction: HomeInteraction, modifier: Modifier = Modifier) {

    AnimatedVisibility(
        visible = state.isWelcomeContentVisible,
        enter = fadeIn(tween(350)),
        exit = fadeOut(tween(350))
    ){
        WelcomeSection(interaction = interaction)
    }

    AnimatedVisibility(
        visible = state.isCoffeeSelectionContentVisible,
        enter = fadeIn(tween(350)),
        exit = fadeOut(tween(350)) + slideOutHorizontally()
    ){
        CoffeeSelectionSection(state = state, interaction = interaction)
    }
}



