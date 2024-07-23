package de.gmuth.overarch

open class System(
    id: Id,
    external: Boolean? = null,
    name: String? = null,
    desc: String? = null
) :
    Node(id, Type.SYSTEM, name, desc, external = external)