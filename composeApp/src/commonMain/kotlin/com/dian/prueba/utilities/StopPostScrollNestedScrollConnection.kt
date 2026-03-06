package com.dian.prueba.utilities

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity

// ref: https://github.com/cliuff/boundo/commit/5d179b
// ref: https://issuetracker.google.com/issues/353304855
object StopPostScrollNestedScrollConnection : NestedScrollConnection {
    override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource) =
        available.copy(x = 0f)
    override suspend fun onPostFling(consumed: Velocity, available: Velocity) =
        available.copy(x = 0f)
}