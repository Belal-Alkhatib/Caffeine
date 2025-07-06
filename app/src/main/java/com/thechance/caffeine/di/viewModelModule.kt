package com.thechance.caffeine.di

import com.thechance.caffeine.ui.screen.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{
    viewModel<HomeViewModel> { HomeViewModel()}
}