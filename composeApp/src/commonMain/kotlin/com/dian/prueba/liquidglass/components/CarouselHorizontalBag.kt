package com.dian.prueba.liquidglass.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dian.prueba.data.model.LocalColors

@ExperimentalMaterial3Api
@Composable
fun CarouselHorizontalSample() {
    val state = rememberCarouselState(itemCount = { 10 }, initialItem = 0)
    val colorModifier = LocalColors.current

    Column(verticalArrangement = Arrangement.Center) {
        HorizontalUncontainedCarousel( // HorizontalMultiBrowseCarousel or HorizontalUncontainedCarousel
            state = state,
            //preferredItemWidth = 250.dp,
            itemWidth = 250.dp,
            modifier = Modifier.height(200.dp),
            itemSpacing = 8.dp
        ) { page ->
            Box(
                modifier =
                    Modifier
                        .padding(10.dp)
                        .background(colorModifier.logoColorLight)
                        .fillMaxSize()
                        .aspectRatio(0.5f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = page.toString(), fontSize = 32.sp, color = Color.White)
            }
        }
    }
}