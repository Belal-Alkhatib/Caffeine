package com.thechance.caffeine.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.thechance.caffeine.R
import com.thechance.caffeine.ui.screen.home.CoffeeCupSize
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

@Composable
fun getMlByCoffeeCupSize(coffeeCupSize: CoffeeCupSize): String {
    return when(coffeeCupSize){
        CoffeeCupSize.SMALL -> stringResource(R.string._150_ml)
        CoffeeCupSize.MEDIUM -> stringResource(R.string._200_ml)
        CoffeeCupSize.LARGE -> stringResource(R.string._400_ml)
    }
}