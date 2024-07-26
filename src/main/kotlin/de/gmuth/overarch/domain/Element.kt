package de.gmuth.overarch.domain

open class Element(
    val id: Id,
    val type: Type,
    val name: String? = null,
    val desc: String? = null,
    val tech: String? = null,
    val subtype: String? = null,
) {
    enum class Type {
        PERSON, COMPONENT, CONTAINER, SYSTEM, REL, PUBLISH, SUBSCRIBE;

        override fun toString() = name.lowercase()
    }

    companion object {
        private val elementMap: MutableMap<Id, Element> = mutableMapOf()
        fun get(id: Id) = elementMap.get(id)
        val allElements: Collection<Element>
            get() = elementMap.values
    }

    init {
        elementMap[id] = this
        println("| $type[$id] ${name ?: ""}")
    }
}