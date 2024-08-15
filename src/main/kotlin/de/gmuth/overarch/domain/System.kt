package de.gmuth.overarch.domain

open class System(
    id: Id,
    external: Boolean? = null,
    name: String? = null,
    desc: String? = null
) : Node(
    id = id,
    type = Type.SYSTEM,
    name = name,
    desc = desc,
    external = external
) {
    fun sendsTo(desc: String, target: Node) =
        rel(buildRelId("sends-to", target), target, "sends", desc, tech, Type.SEND)
}
