package com.thechance.caffeine.ui.screen.home

import kotlinx.coroutines.flow.MutableStateFlow

data class HomeUiState(
    val isWelcomeContentVisible: Boolean = true,

    val isCoffeeSelectionContentVisible: Boolean = false,
    val coffeeTypes: List<CoffeeType> = CoffeeType.entries.toList(),
    val selectedCoffeeType: CoffeeType = CoffeeType.BLACK

    )

enum class CoffeeType {
    BLACK,
    ESPRESSO,
    LATTE,
    MACCHIATO
}

