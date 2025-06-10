package dev.jeziellago.compose.markdowntext.plugins.syntaxhighlight

import org.junit.Assert.assertEquals
import org.junit.Test

class SyntaxHighlightTest {
    @Test
    fun `opening and closing delimiters are double equals`() {
        val highlight = SyntaxHighlight("sample")
        assertEquals("==", highlight.openingDelimiter)
        assertEquals("==", highlight.closingDelimiter)
    }
}
