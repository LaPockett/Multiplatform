package com.dian.prueba.ui.components.buttons

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dian.prueba.model.LocalColors

/**
 * Source: https://www.droidcon.com/2025/04/11/6-steps-to-make-a-slide-to-unlock-button-in-jetpack-compose/
 * This composable creates a custom, swipeable button that resembles a "slide to book" interaction.
 * It is composed of two main parts:
 *  - The outer track (the full-width button background) which displays the label.
 *  - The inner slider thumb, which can be dragged from left to right.
 *
 *
 * @param btnText Text to display on the outer button track (e.g., "Book Ride ₹199")
 * @param btnTextStyle Text style for the button label (e.g., font weight, color)
 * @param outerBtnBackgroundColor Background color for the full-width outer button
 * @param sliderBtnBackgroundColor Background color for the draggable thumb button
 * @param sliderBtnIcon Icon shown inside the slider thumb (e.g., car or arrow icon)
 * @param onBtnSwipe Callback triggered once the user slides to complete the booking
 */
@Composable
fun SlideToBookButton(
    btnText: String,
    outerBtnBackgroundColor: Color,
    sliderBtnBackgroundColor: Color,
    onBtnSwipe: () -> Unit
) {
    // Slider button width
    val sliderButtonWidthDp = 70.dp
    val colorModifier = LocalColors.current
    val density = LocalDensity.current
    val sliderButtonWidthPx = with(density) { sliderButtonWidthDp.toPx() }
    var sliderPositionPx by remember { mutableFloatStateOf(0f) }
    var boxWidthPx by remember { mutableIntStateOf(0) }

    var showLoadingIndicator by remember { mutableStateOf(false) }

    val dragProgress = remember(sliderPositionPx, boxWidthPx) {
        if (boxWidthPx > 0) {
            (sliderPositionPx / (boxWidthPx - sliderButtonWidthPx)).coerceIn(0f, 1f)
        } else {
            0f
        }
    }
    val textAlpha = 1f - dragProgress
    var sliderComplete by remember { mutableStateOf(false) }
    val trackScale by animateFloatAsState(
        targetValue = if (sliderComplete) 0f else 1f,
        animationSpec = tween(durationMillis = 300), label = "trackScale"
    )
    val sliderAlpha by animateFloatAsState(
        targetValue = if (sliderComplete) 0f else 1f,
        animationSpec = tween(durationMillis = 300), label = "sliderAlpha"
    )
    LaunchedEffect(dragProgress) {
        if (dragProgress >= 0.8f && !sliderComplete) {
            sliderComplete = true
            showLoadingIndicator = true
            onBtnSwipe()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(63.dp)
            .onSizeChanged { boxWidthPx = it.width }
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer(scaleX = trackScale, scaleY = 1f)
                .background(
                    color = outerBtnBackgroundColor,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Text(
                text = btnText,
                style = MaterialTheme.typography.bodyMedium,
                color = colorModifier.logoColor,
                modifier = Modifier.align(Alignment.Center)
                    .alpha(textAlpha)
            )
        }

        // Slider thumb container, positioned at the left edge of the button
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(1.dp)
                .offset(x = with(density) { sliderPositionPx.toDp() })
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        // Calculate new potential position
                        val newPosition = sliderPositionPx + delta
                        // Clamp it within 0 to (totalWidth - slider button width)
                        val maxPosition = boxWidthPx - sliderButtonWidthPx
                        sliderPositionPx = newPosition.coerceIn(0f, maxPosition)
                    },
                    onDragStarted = { /* Optional: add feedback or animation here */ },
                    onDragStopped = {
                        // TODO: In next step, we’ll trigger onBtnSwipe if drag passes threshold
                    }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .alpha(sliderAlpha)
                    .graphicsLayer { alpha = sliderAlpha }
            ) {
                SliderButton(
                    sliderBtnWidth = sliderButtonWidthDp,
                    sliderBtnBackgroundColor = sliderBtnBackgroundColor
                )
            }
        }
        /**
         * Show the loading indicator after the slider reaches the end and the animation completes
         */
        if (showLoadingIndicator) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = colorModifier.logoColorLight
            )
        }
    }
}

/**
 *
 * This composable defines the visual appearance of the slider thumb — a small rounded box
 * that contains an icon (usually a car or arrow). It is positioned inside the larger
 * SlideToBookButton and will later be made draggable.
 *
 * @param sliderBtnBackgroundColor Background color for the thumb (distinct from the track)
 * @param sliderBtnIcon Icon displayed at the center of the thumb button
 */
@Composable
private fun SliderButton(
    sliderBtnWidth: Dp, // Width of the button
    sliderBtnBackgroundColor: Color, // Background color for the thumb
) {
    // Root Box for the slider thumb
    Box(
        modifier = Modifier
            .wrapContentSize()
            .width(70.dp)
            .height(62.dp)
            .background(
                color = sliderBtnBackgroundColor,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        /*Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "Car Icon",
                modifier = Modifier.size(36.dp)
            )
        }*/
    }
}