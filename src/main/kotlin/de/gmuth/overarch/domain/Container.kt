package de.gmuth.overarch.domain

open class Container(
    id: Id,
    name: String? = null,
    desc: String? = null,
    tech: String? = null,
    subtype: String? = null,
    external: Boolean? = null
) : Node(
    id = id,
    type = Type.CONTAINER,
    name = name,
    desc = desc,
    tech = tech,
    subtype = subtype,
    external = external
)