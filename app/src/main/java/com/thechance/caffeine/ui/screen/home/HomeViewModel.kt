package com.thechance.caffeine.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(): ViewModel(), HomeInteraction {
    private val _state: MutableStateFlow<HomeUiState> by lazy { MutableStateFlow(HomeUiState()) }
    val state = _state.asStateFlow()

    override fun onBringCoffeeClick() {
        _state.update { it.copy(isWelcomeContentVisible = false, isCoffeeSelectionContentVisible = true) }
    }

    override fun onContinueToPrepareCoffeeClick() {
        _state.update { it.copy(isCoffeeSelectionContentVisible = false, isCoffeeCustomizationContentVisible = true) }
    }

    override fun onSelectCoffeeChange(selectedCoffeeType: CoffeeType) {
        _state.update { it.copy(selectedCoffeeType = selectedCoffeeType) }
    }

    override fun onContinueToDoneCoffeeClick() {
        _state.update { it.copy(isCoffeeSelectorsVisible = false) }
    }

    override fun onSelectCoffeeCupSizeChange(selectedCoffeeCupSize: CoffeeCupSize) {
        _state.update { it.copy(selectedCoffeeCupSize = selectedCoffeeCupSize) }
    }

    override fun onSelectCoffeeStrengthChange(selectedCoffeeStrength: CoffeeStrength) {
        if (selectedCoffeeStrength == _state.value.selectedCoffeeStrength) {
            // User clicked the same strength again, animate out
            _state.update {
                it.copy(
                    coffeeStrengthAnimationState = CoffeeStrengthAnimationState.EXITING
                )
            }

        } else {
            // User selected new strength, animate in
            _state.update {
                it.copy(
                    selectedCoffeeStrength = selectedCoffeeStrength,
                    coffeeStrengthAnimationState = CoffeeStrengthAnimationState.ENTERING
                )
            }
        }

        viewModelScope.launch {
            delay(1800)
            _state.update {
                it.copy(coffeeStrengthAnimationState = CoffeeStrengthAnimationState.NONE)
            }
        }
    }

//    override fun onSelectCoffeeStrengthChange(selectedCoffeeStrength: CoffeeStrength) {
//        _state.update { it.copy(selectedCoffeeStrength = selectedCoffeeStrength, isAnotherStrengthSelected = true) }
//        viewModelScope.launch {
//            delay(2000)
//            _state.update { it.copy(isAnotherStrengthSelected = false) }
//        }
//
//    }

}


