package dev.jeziellago.compose.markdowntext.plugins.syntaxhighlight

import org.commonmark.parser.Parser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SyntaxHighlightExtensionTest {
    @Test
    fun `parser creates SyntaxHighlight node for double equals`() {
        val parser = Parser.builder().extensions(setOf(SyntaxHighlightExtension.create())).build()
        val document = parser.parse("==code==")
        val paragraph = document.firstChild
        val highlight = paragraph.firstChild
        assertTrue(highlight is SyntaxHighlight)
        val text = (highlight as SyntaxHighlight).firstChild as org.commonmark.node.Text
        assertEquals("code", text.literal)
    }
}
