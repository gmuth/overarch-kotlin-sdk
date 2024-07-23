package de.gmuth.overarch

enum class Direction {
    up, down, left, right;

    override fun toString() = ":direction :$name"
}