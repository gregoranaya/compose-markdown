package com.bcp.com.markdown.plugins.syntaxhighlight

import org.commonmark.Extension
import org.commonmark.parser.Parser
import org.commonmark.parser.Parser.ParserExtension
import org.commonmark.renderer.text.TextContentRenderer
import org.commonmark.renderer.text.TextContentRenderer.TextContentRendererExtension

class BCPSyntaxHighlightExtension private constructor() : ParserExtension,
    TextContentRendererExtension {
    override fun extend(parserBuilder: Parser.Builder) {
        parserBuilder.customDelimiterProcessor(
            BCPSyntaxHighlightDelimiterProcessor(
                openingCharacter = '=',
                closingCharacter = '=',
                minLength = 2,
            ),
        )
    }

    override fun extend(rendererBuilder: TextContentRenderer.Builder) {
        rendererBuilder.nodeRendererFactory { context ->
            BCPSyntaxHighlightNodeRenderer(
                context
            )
        }
    }

    companion object {
        fun create(): Extension {
            return BCPSyntaxHighlightExtension()
        }
    }
}
