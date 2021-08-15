package com.paavam.utils

fun String.isAlphanumeric() = matches("[a-zA-Z0-9]+".toRegex())

fun String.isMobileNumber() = matches("[0-9]{10,}".toRegex())