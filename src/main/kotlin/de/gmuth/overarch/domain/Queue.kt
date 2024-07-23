package de.gmuth.overarch.domain

open class Queue(
    id: Id,
    name: String? = null,
    desc: String? = null,
    tech: String? = null,
    external: Boolean? = null,
) : Container(
    id,
    name = name,
    desc = desc,
    subtype = "queue",
    tech = tech,
    external = external,
)