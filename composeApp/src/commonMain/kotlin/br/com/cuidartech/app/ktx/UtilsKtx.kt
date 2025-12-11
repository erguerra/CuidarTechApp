package br.com.cuidartech.app.ktx

fun String.appendEllipsisIfLonger(originalLength: Int): String {
    return if (originalLength > this.length) "${this.trim()}..." else this
}

fun String.sanitizeLineBreaks(): String =
    this.replace('\n', ' ').replace('\r', ' ')