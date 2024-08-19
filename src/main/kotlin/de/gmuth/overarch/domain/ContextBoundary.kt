package de.gmuth.overarch.domain

open class ContextBoundary(
    id: Id,
    name: String? = null,
    desc: String? = null,
    tags: Collection<String> = emptyList(),
    val elements: Collection<Element> = emptyList()
) : Node(
    id = id,
    type = Type.CONTEXT_BOUNDARY,
    name = name,
    desc = desc,
    tags = tags,
)
