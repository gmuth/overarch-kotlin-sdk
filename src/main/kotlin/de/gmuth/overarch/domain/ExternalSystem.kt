package de.gmuth.overarch.domain

open class ExternalSystem(
    id: Id,
    name: String? = null,
    desc: String? = null
) : System(
    id,
    name = name,
    desc = desc,
    external = true
)
