package de.gmuth.overarch.domain

open class Element(
    val id: Id,
    val type: Type,
    val name: String? = null,
    val desc: String? = null,
    val tech: String? = null,
    val subtype: String? = null,
    val tags: Collection<String> = emptyList()
) {
    enum class Type {
        PERSON, COMPONENT, CONTAINER, SYSTEM, CONTEXT_BOUNDARY,
        REL, PUBLISH, SUBSCRIBE, SEND, REQUEST;

        override fun toString() = name
            .lowercase()
            .replace("_", "-")
    }

    companion object {
        private val elementMap: MutableMap<Id, Element> = mutableMapOf()
        fun get(id: Id) = elementMap.get(id)
        val allElements: Collection<Element>
            get() = elementMap.values
    }

    override fun toString(): String = StringBuilder().apply {
        append("$id ")
        append("(${subtype ?: type})")
        // other attributes might not have useful content yet
    }.toString()

    init {
        elementMap[id] = this
        println("+ $this")
    }
}