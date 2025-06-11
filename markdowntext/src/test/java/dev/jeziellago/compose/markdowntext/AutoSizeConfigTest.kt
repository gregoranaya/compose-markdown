package dev.jeziellago.compose.markdowntext

import android.util.TypedValue
import org.junit.Assert.assertEquals
import org.junit.Test

class AutoSizeConfigTest {
    @Test
    fun `default unit is sp`() {
        val config = AutoSizeConfig(10, 20, 2)
        assertEquals(TypedValue.COMPLEX_UNIT_SP, config.unit)
    }

    @Test
    fun `stores provided values`() {
        val config = AutoSizeConfig(5, 15, 3)
        assertEquals(5, config.autoSizeMinTextSize)
        assertEquals(15, config.autoSizeMaxTextSize)
        assertEquals(3, config.autoSizeStepGranularity)
    }
}
