package de.gmuth.overarch.domain

open class Model(
    initialElements: Set<Element> = emptySet()
) {
    val elements: MutableSet<Element> = mutableSetOf()

    init {
        elements.addAll(initialElements)
    }

    constructor(vararg elements: Element) : this(elements.toSet())

}