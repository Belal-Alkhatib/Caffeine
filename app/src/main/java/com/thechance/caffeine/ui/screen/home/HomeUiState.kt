package com.thechance.caffeine.ui.screen.home

data class HomeUiState(
    val isWelcomeContentVisible: Boolean = true,

    val isCoffeeSelectionContentVisible: Boolean = false,
    val coffeeTypes: List<CoffeeType> = CoffeeType.entries.toList(),
    val selectedCoffeeType: CoffeeType = CoffeeType.BLACK,

    val coffeeSize: CoffeeSize = CoffeeSize.SMALL,
    val coffeeStrength: CoffeeStrength = CoffeeStrength.LOW,
    )

enum class CoffeeType {
    BLACK,
    ESPRESSO,
    LATTE,
    MACCHIATO
}

enum class CoffeeSize{
    SMALL,
    MEDIUM,
    LARGE
}

enum class CoffeeStrength{
    LOW,
    MEDIUM,
    HIGH
}

