package com.thechance.caffeine.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.thechance.caffeine.R
import com.thechance.caffeine.ui.screen.home.CoffeeType

@Composable
fun getCoffeeImageByType(coffeeType: CoffeeType): Painter {
    return when (coffeeType) {
        CoffeeType.BLACK -> painterResource(R.drawable.img_coffee_black)
        CoffeeType.ESPRESSO -> painterResource(R.drawable.img_coffee_espresso)
        CoffeeType.LATTE -> painterResource(R.drawable.img_coffee_latte)
        CoffeeType.MACCHIATO -> painterResource(R.drawable.img_coffee_macchiato)
    }
}