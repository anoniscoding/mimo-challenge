package com.anoniscoding.mimo.util

import android.text.Html
import androidx.core.text.HtmlCompat

fun String.toHtmlSpanned() = Html.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)