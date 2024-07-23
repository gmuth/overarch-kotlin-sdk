package de.gmuth.overarch.domain

open class Model(
    var elements: Set<Element> = mutableSetOf()
) {
    constructor(vararg elements: Element) : this(elements.toSet())
}