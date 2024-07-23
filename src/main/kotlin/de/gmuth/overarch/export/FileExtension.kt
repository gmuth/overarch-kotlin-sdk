package de.gmuth.overarch.export

import java.io.File

fun File.unixPath() = toPath().joinToString("/")