package de.gmuth.overarch.domain

open class View(
    val type: Type,
    val id: Id,
    val title: String,
    val layout: String = "top-down",
    initialElements: Collection<Element>
) {
    enum class Type {
        Container, Context;

        override fun toString() = name.lowercase()
        fun elementType() = "${name.lowercase()}-view"
    }

    val elements: MutableCollection<Element> = mutableListOf()

    init {
        elements.addAll(initialElements)
        println("* $id (${type.elementType()}) ${elements.size} elements")
    }

    fun add(vararg element: Element) =
        addAll(element.toList())

    fun addAll(moreElements: Collection<Element>) =
        elements.addAll(moreElements)

}
