package com.dian.prueba.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dian.prueba.data.globalResources.LocalColors
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.abs
import kotlin.time.Clock

/**
 * Source: https://gist.github.com/Rohit-554/9e755b1133a82b4173a37bd7f2a48031
 */
@Preview
@Composable
fun CarouselCalendar() {
    //// Clock.System.now() -> Output: 2025-11-15T14:30:45.123456789Z
    ////                        (Year-Month-Day T Hour:Minute:Second.Nanoseconds Z for UTC)

    //// Output EP: 2025-11-15T20:00:45.123456789 (if you're in IST, UTC+5:30)
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    // Output EP: 2025-11-15
    val year = today.year // 2026
    // Always start from 1st January
    val startDate = LocalDate(year, today.month, today.day-3)
    val endDate = LocalDate(year, 12, 31)

    // Generate full year date list
    val dates = remember {
        generateSequence(startDate) { date ->
            val next = date.plus(1, DateTimeUnit.DAY)
            if (next <= endDate) next else null
        }.toList()
    }

    // Selected item is today's date (even though list starts at Jan 1)
    var selectedDate by remember { mutableStateOf(today) }

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        DialerWeekCalendar(
            dates = dates,
            selectedDate = selectedDate,
            onDateSelected = { selectedDate = it },
            maxWidth = maxWidth
        )
    }
}

@Composable
fun DialerWeekCalendar(
    modifier: Modifier = Modifier,
    dates: List<LocalDate>,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    maxWidth: Dp
) {
    val listState = rememberLazyListState()
    var isInitialLoad by remember { mutableStateOf(true) }
    val colorModifier = LocalColors.current

    // Auto-scroll to center the selected date
    LaunchedEffect(selectedDate) {
        val selectedIndex = dates.indexOf(selectedDate)
        if (selectedIndex != -1) {
            if (isInitialLoad) {
                listState.scrollToItem(selectedIndex)
                isInitialLoad = false
            } else {
                listState.animateScrollToItem(selectedIndex)
            }
        }
    }

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = (maxWidth / 2) - 46.dp)
    ) {
        items(dates.size) { index ->
            val date = dates[index]

            // Center calculation
            val layoutInfo = listState.layoutInfo
            val viewportCenter =
                layoutInfo.viewportStartOffset +
                        (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset) / 2

            val itemInfo = layoutInfo.visibleItemsInfo.find { it.index == index }
            val itemCenter = itemInfo?.let { it.offset + it.size / 2 } ?: 0

            // Distance from center
            val distanceFromCenter = if (itemInfo != null) {
                abs(viewportCenter - itemCenter).toFloat() / layoutInfo.viewportSize.width
            } else 1f

            val scale = (1f - (distanceFromCenter * 0.3f)).coerceIn(0.7f, 1f)
            val rotationY = (distanceFromCenter * 40f).coerceAtMost(45f)
            val alpha = (1f - (distanceFromCenter * 0.5f)).coerceIn(0.5f, 1f)

            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.rotationY =
                            if (itemCenter < viewportCenter) rotationY else -rotationY
                        this.alpha = alpha
                    }
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (date == selectedDate) colorModifier.logoColor else Color.White
                    )
                    .clickable { onDateSelected(date) }
                    .width(80.dp)
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = date.month.name.take(3),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = if (date == selectedDate) Color.White.copy(alpha = 0.8f)
                            else Color.DarkGray
                        )
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = date.day.toString(),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (date == selectedDate) Color.White else Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = date.dayOfWeek.name.take(3),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = if (date == selectedDate) Color.White.copy(alpha = 0.8f)
                            else Color.Gray
                        )
                    )
                }
            }
        }
    }
}