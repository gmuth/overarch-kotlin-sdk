package de.gmuth.overarch

import java.io.File

fun File.unixPath() = toPath().joinToString("/")