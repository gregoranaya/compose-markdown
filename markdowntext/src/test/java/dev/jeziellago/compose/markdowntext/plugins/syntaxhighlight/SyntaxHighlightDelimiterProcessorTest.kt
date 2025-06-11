package dev.jeziellago.compose.markdowntext.plugins.syntaxhighlight

import org.commonmark.node.Text
import org.commonmark.parser.delimiter.DelimiterRun
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito

class SyntaxHighlightDelimiterProcessorTest {
    @Test
    fun `getDelimiterUse returns min length when runs are long enough`() {
        val processor = SyntaxHighlightDelimiterProcessor('=', '=', 2)
        val opener = Mockito.mock(DelimiterRun::class.java)
        val closer = Mockito.mock(DelimiterRun::class.java)
        Mockito.`when`(opener.length()).thenReturn(2)
        Mockito.`when`(closer.length()).thenReturn(3)
        val result = processor.getDelimiterUse(opener, closer)
        assertEquals(2, result)
    }

    @Test
    fun `getDelimiterUse returns zero when runs are short`() {
        val processor = SyntaxHighlightDelimiterProcessor('=', '=', 2)
        val opener = Mockito.mock(DelimiterRun::class.java)
        val closer = Mockito.mock(DelimiterRun::class.java)
        Mockito.`when`(opener.length()).thenReturn(1)
        Mockito.`when`(closer.length()).thenReturn(2)
        val result = processor.getDelimiterUse(opener, closer)
        assertEquals(0, result)
    }

    @Test
    fun `process wraps nodes between opener and closer`() {
        val processor = SyntaxHighlightDelimiterProcessor('=', '=', 2)
        val opener = Text("open")
        val inside = Text("body")
        val closer = Text("close")
        opener.appendChild(inside)
        opener.appendChild(closer)
        processor.process(opener, closer, 2)
        val highlight = opener.next
        assertTrue(highlight is SyntaxHighlight)
        assertEquals(inside, (highlight as SyntaxHighlight).firstChild)
    }
}
