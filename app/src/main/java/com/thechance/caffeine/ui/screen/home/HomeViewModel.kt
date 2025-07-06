package com.thechance.caffeine.ui.screen.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(): ViewModel(), HomeInteraction {
    private val _state: MutableStateFlow<HomeUiState> by lazy { MutableStateFlow(HomeUiState()) }
    val state = _state.asStateFlow()

    override fun onBringCoffeeClick() {
        //todo make bring coffee content visible also
        _state.update { it.copy(isWelcomeContentVisible = false) }
    }

}


