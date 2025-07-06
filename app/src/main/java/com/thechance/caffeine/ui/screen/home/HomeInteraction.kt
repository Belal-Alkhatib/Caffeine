package com.thechance.caffeine.ui.screen.home

interface HomeInteraction {
    fun onBringCoffeeClick()
    fun onContinueToPrepareCoffeeClick()
    fun onSelectCoffeeChange(selectedCoffeeType: CoffeeType)
}