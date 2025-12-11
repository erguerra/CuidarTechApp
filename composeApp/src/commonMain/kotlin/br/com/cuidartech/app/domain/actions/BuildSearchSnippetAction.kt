package br.com.cuidartech.app.domain.actions

import br.com.cuidartech.app.ktx.appendEllipsisIfLonger

class BuildSearchSnippetAction {

    operator fun invoke(snippet: String?, query: String, radius: Int = DEFAULT_RADIUS_SIZE): String {
        if(snippet == null) {
            return ""
        }
        val index = snippet.indexOf(query, ignoreCase = true)
        if (index < 0) return snippet.take(radius).appendEllipsisIfLonger(snippet.length)

        val start = (index - radius).coerceAtLeast(0)
        val end = (index + query.length + radius).coerceAtMost(snippet.length)
        val prefixEllipsis = if (start > 0) "..." else ""
        val suffixEllipsis = if (end < snippet.length) "..." else ""
        return prefixEllipsis + snippet.substring(start, end).trim() + suffixEllipsis
    }

    companion object Companion {
        private const val DEFAULT_RADIUS_SIZE = 60
    }
}

