package com.bcp.com.markdown.plugins.syntaxhighlight

import org.commonmark.node.CustomNode
import org.commonmark.node.Delimited

class BCPSyntaxHighlight(val textLiteral: String) : CustomNode(), Delimited {
    override fun getOpeningDelimiter(): String {
        return DELIMITER
    }

    override fun getClosingDelimiter(): String {
        return DELIMITER
    }

    companion object {
        private const val DELIMITER = "=="
    }
}
