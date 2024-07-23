package de.gmuth.overarch.domain

enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    override fun toString() = name.lowercase()
}