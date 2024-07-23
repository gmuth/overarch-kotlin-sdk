package de.gmuth.overarch.domain

open class Rel(
    id: Id,
    type: Type = Type.REL,
    val from: Node? = null,
    val to: Node? = null,
    name: String? = null,
    desc: String? = null,
    tech: String? = null,
) : Element(
    id = id,
    type = type,
    name = name,
    desc = desc,
    tech = tech
) {
    var direction: Direction? = null

    val nodes: Collection<Node>
        get() = mutableListOf<Node>().apply {
            from?.let { add(it) }
            to?.let { add(it) }
        }

    fun up() = this.apply { direction = Direction.UP }
    fun down() = this.apply { direction = Direction.DOWN }
    fun left() = this.apply { direction = Direction.LEFT }
    fun right() = this.apply { direction = Direction.RIGHT }
}
