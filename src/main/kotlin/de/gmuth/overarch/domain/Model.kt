package de.gmuth.overarch.domain

open class Model(
    vararg collection: Collection<Element>
) {
    val elements: MutableSet<Element> = mutableSetOf()

    init {
        collection.forEach { elements.addAll(it) }
    }

    constructor(vararg elements: Element) : this(elements.toSet())

}