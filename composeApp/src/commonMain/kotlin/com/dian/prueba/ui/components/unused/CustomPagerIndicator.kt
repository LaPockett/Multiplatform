package com.dian.prueba.ui.components.unused

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.abs


/* *
 * Custom Pager Indicator
 * - Coge el total de número de dots y no se ve estético, sería interesante
 * mejorarlo para que solo salgan por ejemplo 5 dots y que a partir de ahí
 * haya "animaciones".
 * Hacer algo parecido a la paginación con dots en las publicaciones de Instagram
 */
//ref: https://github.com/jschamburger/compose-custom-pagerindicator/blob/pager-library-0.24/app/src/main/java/de/jschamburger/compose/custompagerindicator/MainActivity.kt

private const val MULTIPLIER_SELECTED_PAGE = 4
private val baseWidth = 4.dp
private val spacing = 10.dp
private val height = 8.dp

@Composable
fun CustomPagerIndicator(
    pagerState: PagerState,
    indicatorColor: Color
) {
    Row (
        modifier = Modifier.fillMaxSize()
            .padding(bottom = 12.dp),
        verticalAlignment = Alignment.Bottom
    ){
        val offsetIntPart = pagerState.currentPageOffsetFraction.toInt()
        val offsetFractionalPart = pagerState.currentPageOffsetFraction - offsetIntPart
        val currentPage = pagerState.currentPage + offsetIntPart
        val targetPage = if (pagerState.currentPageOffsetFraction < 0) currentPage - 1 else currentPage + 1
        val currentPageWidth = baseWidth * (1 + (1 - abs(offsetFractionalPart)) * MULTIPLIER_SELECTED_PAGE)
        val targetPageWidth = baseWidth * (1 + abs(offsetFractionalPart) * MULTIPLIER_SELECTED_PAGE)

        repeat(pagerState.pageCount) { index ->
            val width = when (index) {
                currentPage -> currentPageWidth
                targetPage -> targetPageWidth
                else -> baseWidth
            }
            Box(
                modifier = Modifier
                    .width(width)
                    .clip(CircleShape)
                    .background(indicatorColor)
                    .height(height)
            )
            if (index != pagerState.pageCount - 1) {
                Spacer(modifier = Modifier.width(spacing))
            }
        }
    }
}