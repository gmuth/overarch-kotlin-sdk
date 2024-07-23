package de.gmuth.overarch

open class Queue(
    id: Id,
    name: String? = null,
    desc: String? = null,
    external: Boolean? = null,
    tech: String? = null
) : Container(id, name = name, desc = desc, subtype = "queue", external = external, tech = tech)