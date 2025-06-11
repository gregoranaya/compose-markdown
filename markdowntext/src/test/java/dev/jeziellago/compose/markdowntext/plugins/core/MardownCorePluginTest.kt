package dev.jeziellago.compose.markdowntext.plugins.core

import org.commonmark.node.BulletList
import org.commonmark.node.ListItem
import org.commonmark.node.Node
import org.commonmark.node.OrderedList
import org.commonmark.node.Paragraph
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MardownCorePluginTest {
    private fun invokeListLevel(plugin: MardownCorePlugin, node: Node): Int {
        val method = MardownCorePlugin::class.java.getDeclaredMethod("listLevel", Node::class.java)
        method.isAccessible = true
        return method.invoke(plugin, node) as Int
    }

    private fun invokeIsInTightList(plugin: MardownCorePlugin, paragraph: Paragraph): Boolean {
        val method = MardownCorePlugin::class.java.getDeclaredMethod("isInTightList", Paragraph::class.java)
        method.isAccessible = true
        return method.invoke(plugin, paragraph) as Boolean
    }

    @Test
    fun `list level counts ancestors`() {
        val plugin = MardownCorePlugin(0, 0, true)
        val root = ListItem()
        val child = ListItem()
        val grand = ListItem()
        root.appendChild(child)
        child.appendChild(grand)

        assertEquals(0, invokeListLevel(plugin, root))
        assertEquals(1, invokeListLevel(plugin, child))
        assertEquals(2, invokeListLevel(plugin, grand))
    }

    @Test
    fun `paragraph tight list detection`() {
        val plugin = MardownCorePlugin(0, 0, true)
        val bullet = BulletList().apply { isTight = true }
        val item = ListItem()
        val paragraph = Paragraph()
        bullet.appendChild(item)
        item.appendChild(paragraph)

        assertTrue(invokeIsInTightList(plugin, paragraph))
    }
}
