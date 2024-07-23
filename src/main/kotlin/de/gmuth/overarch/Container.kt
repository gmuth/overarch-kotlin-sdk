package de.gmuth.overarch

open class Container(
    id: Id,
    external: Boolean? = null,
    name: String? = null,
    desc: String? = null,
    tech: String? = null,
    subtype: String? = null
) :
    Node(
        id,
        Type.CONTAINER,
        name = name,
        desc = desc,
        tech = tech,
        subtype = subtype,
        external = external
    ) {
    constructor(idName: String, nameSpace: String) : this(Id(idName, nameSpace))
}