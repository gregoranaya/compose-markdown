package com.bcp.com.markdown

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.text.Spanned
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.bcp.com.markdown.plugins.core.BCPMardownCorePlugin
import com.bcp.com.markdown.plugins.image.BCPImagesPlugin
import com.bcp.com.markdown.plugins.syntaxhighlight.BCPSyntaxHighlightPlugin
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonConfiguration
import io.noties.markwon.SoftBreakAddsNewLinePlugin
import io.noties.markwon.core.MarkwonTheme
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.linkify.LinkifyPlugin

internal object BCPMarkdownRender {

    fun create(
        context: Context,
        imageLoader: ImageLoader?,
        linkifyMask: Int,
        enableSoftBreakAddsNewLine: Boolean,
        syntaxHighlightColor: Color,
        syntaxHighlightTextColor: Color,
        headingBreakColor: Color,
        enableUnderlineForLink: Boolean,
        beforeSetMarkdown: ((TextView, Spanned) -> Unit)? = null,
        afterSetMarkdown: ((TextView) -> Unit)? = null,
        onLinkClicked: ((String) -> Unit)? = null,
        style: TextStyle
    ): Markwon {
        val coilImageLoader = imageLoader ?: ImageLoader.Builder(context)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()

        return Markwon.builderNoCore(context)
            .usePlugin(
                BCPMardownCorePlugin(
                    syntaxHighlightColor.toArgb(),
                    syntaxHighlightTextColor.toArgb(),
                    enableUnderlineForLink,
                )
            )
            .usePlugin(HtmlPlugin.create())
            .usePlugin(BCPImagesPlugin.create(context, coilImageLoader))
            .usePlugin(StrikethroughPlugin.create())
            .usePlugin(TablePlugin.create(context))
            .usePlugin(LinkifyPlugin.create(linkifyMask))
            .usePlugin(TaskListPlugin.create(context))
            .apply {
                if (enableSoftBreakAddsNewLine) {
                    usePlugin(SoftBreakAddsNewLinePlugin.create())
                }
            }
            .usePlugin(BCPSyntaxHighlightPlugin())
            .usePlugin(object : AbstractMarkwonPlugin() {

                override fun beforeSetText(textView: TextView, markdown: Spanned) {
                    beforeSetMarkdown?.invoke(textView, markdown)
                }

                override fun afterSetText(textView: TextView) {
                    afterSetMarkdown?.invoke(textView)
                }

                override fun configureTheme(builder: MarkwonTheme.Builder) {
                    if (headingBreakColor == Color.Transparent) {
                        builder.headingBreakColor(1)
                    } else {
                        builder.headingBreakColor(headingBreakColor.toArgb())
                    }
                    builder.bulletWidth(style.fontSize.value.toInt())
                }

                override fun configureConfiguration(builder: MarkwonConfiguration.Builder) {
                    // Setting [MarkwonConfiguration.Builder.linkResolver] overrides
                    // Markwon's default behaviour - see Markwon's [LinkResolverDef]
                    // and how it's used in [MarkwonConfiguration.Builder].
                    // Only use it if the client explicitly wants to handle link clicks.
                    onLinkClicked ?: return
                    builder.linkResolver { _, link ->
                        // handle individual clicks on Textview link
                        onLinkClicked.invoke(link)
                    }
                }
            })
            .build()
    }
}
