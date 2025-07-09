package com.thechance.caffeine.ui.screen.home

data class HomeUiState(
    val isWelcomeContentVisible: Boolean = true,

    val isCoffeeSelectionContentVisible: Boolean = false,
    val isCoffeeSelectorsVisible: Boolean = true,
    val coffeeTypes: List<CoffeeType> = CoffeeType.entries.toList(),
    val selectedCoffeeType: CoffeeType = CoffeeType.BLACK,

    val isCoffeeCustomizationContentVisible: Boolean = false,
    val selectedCoffeeCupSize: CoffeeCupSize = CoffeeCupSize.SMALL,
    val selectedCoffeeStrength: CoffeeStrength = CoffeeStrength.LOW,
    val isAnotherStrengthSelected: Boolean = false,

    val lastSelectedStrength: CoffeeStrength? = null,
    val isStrengthAnimationVisible: Boolean = false,
    val coffeeStrengthAnimationState: CoffeeStrengthAnimationState = CoffeeStrengthAnimationState.NONE,


    )

enum class CoffeeType {
    BLACK,
    ESPRESSO,
    LATTE,
    MACCHIATO
}

enum class CoffeeCupSize{
    SMALL,
    MEDIUM,
    LARGE
}

enum class CoffeeStrength{
    LOW,
    MEDIUM,
    HIGH
}

enum class CoffeeStrengthAnimationState {
    NONE, ENTERING, EXITING
}


