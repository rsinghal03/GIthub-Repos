package com.example.githubrepos.extension

private const val NUMBER_PATTERN = "{/number}"

fun String.replaceNumber(): String {
    return this.replace(NUMBER_PATTERN, "", true)
}
