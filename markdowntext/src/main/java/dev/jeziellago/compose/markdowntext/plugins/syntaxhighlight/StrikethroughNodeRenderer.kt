package com.bcp.com.markdown.plugins.syntaxhighlight;

import org.commonmark.node.Node
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.text.TextContentNodeRendererContext
import org.commonmark.renderer.text.TextContentWriter
import java.util.Collections

abstract class BCPSyntaxHighlightRenderer : NodeRenderer {
    override fun getNodeTypes(): MutableSet<Class<out Node>> {
        return Collections.singleton(BCPSyntaxHighlight::class.java)
    }
}

class BCPSyntaxHighlightNodeRenderer(private val context: TextContentNodeRendererContext) :
    BCPSyntaxHighlightRenderer() {
    private val textContent: TextContentWriter = context.writer

    override fun render(node: Node) {
        textContent.write('/')
        renderChildren(node)
        textContent.write('/')
    }

    private fun renderChildren(parent: Node) {
        var node = parent.firstChild
        while (node != null) {
            val next = node.next
            context.render(node)
            node = next
        }
    }
}
